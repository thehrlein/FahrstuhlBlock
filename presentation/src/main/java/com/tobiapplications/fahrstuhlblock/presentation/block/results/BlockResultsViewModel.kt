package com.tobiapplications.fahrstuhlblock.presentation.block.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.DeleteRoundData
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.InsertRoundData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.*
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.*
import com.tobiapplications.fahrstuhlblock.interactor.usecase.invoke
import com.tobiapplications.fahrstuhlblock.interactor.usecase.user.IsShowTrumpDialogEnabledUseCase
import com.tobiapplications.fahrstuhlblock.presentation.SingleLiveEvent
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

private const val WINNER_POSITION = 1

class BlockResultsViewModel(
    private val getGameUseCase: GetGameUseCase,
    private val getBlockResultsUseCase: GetBlockResultsUseCase,
    private val getGameScoresUseCase: GetGameScoresUseCase,
    private val storeRoundUseCase: StoreRoundUseCase,
    private val isShowTrumpDialogEnabledUseCase: IsShowTrumpDialogEnabledUseCase,
    private val storeGameFinishedUseCase: StoreGameFinishedUseCase,
    private val removeRoundUseCase: RemoveRoundUseCase
) : BaseViewModel(), BlockResultsInteractions {

    private val _inputType = MutableLiveData<InputType>()
    val inputType: LiveData<InputType> = _inputType
    private val _blockItems = MutableLiveData<List<BlockItem>>()
    val blockItems: LiveData<List<BlockItem>> = _blockItems
    private val _columnCount = MutableLiveData<Int>()
    val columnCount: LiveData<Int> = _columnCount
    private val game = MutableLiveData<Game>()
    private val _showGameFinishedEvent = SingleLiveEvent<Unit>()
    val showGameFinishedEvent: LiveData<Unit> = _showGameFinishedEvent
    private val gameScores = MediatorLiveData<GameScoreData>().also { mediator ->
        mediator.addSource(game) {
            viewModelScope.launch {
                when (val result = getGameScoresUseCase.invoke(it)) {
                    is AppResult.Success -> {
                        mediator.postValue(result.value)
                        if (result.value.finished && result.value.winnerAlreadyShown.not()) {
                            onGameFinished(result.value.results)
                        }
                    }
                    is AppResult.Error -> Unit
                }
            }
        }
    }

    private val _gameFinished = MediatorLiveData<Boolean>().also { mediator ->
        mediator.addSource(gameScores) {
            mediator.postValue(it.finished)
        }
    }

    val gameFinished: LiveData<Boolean> = _gameFinished
    private val _editInputEnabled = MediatorLiveData<Boolean>().also { mediator ->
        mediator.addSource(blockItems) {
            mediator.postValue(it.filterIsInstance<BlockResult>().isNotEmpty() && _gameFinished.value != false)
        }
        mediator.addSource(gameFinished) {
            mediator.postValue(it != true && _blockItems.value?.filterIsInstance<BlockResult>()?.isNotEmpty() == true)
        }
    }
    val editInputEnabled: LiveData<Boolean> = _editInputEnabled
    private val _finishEarlyEnabled = MediatorLiveData<Boolean>().also { mediator ->
        mediator.addSource(blockItems) {
            mediator.postValue(it.filterIsInstance<BlockResult>().any { it.total != null } && _gameFinished.value != true)
        }
        mediator.addSource(gameFinished) {
            mediator.postValue(
                it != true &&
                    _blockItems.value?.filterIsInstance<BlockResult>()?.any { it.total != null } == true
            )
        }
    }
    val finishEarlyEnabled: LiveData<Boolean> = _finishEarlyEnabled

    fun setGameId(gameId: Long) {
        viewModelScope.launch {
            val currentGame = getCurrentGame(gameId) ?: error("no game found for this gameId")
            game.postValue(currentGame)
            getBlockItems(currentGame)
        }
    }

    private suspend fun getBlockItems(currentGame: Game) {
        when (val result = getBlockResultsUseCase.invoke(currentGame)) {
            is AppResult.Success -> {
                _columnCount.postValue(result.value.columnCount)
                _blockItems.postValue(result.value.items)
                _inputType.postValue(result.value.inputType)
                showTrumpSelectionDialog(result.value)
            }
            is AppResult.Error -> Unit
        }
    }

    private fun isEditInputEnabled(currentGame: Game, blockItemData: BlockItemData): Boolean {
        return currentGame.gameFinished.not() &&
                blockItemData.items.filterIsInstance<BlockResult>().isNotEmpty()
    }

    private fun isFinishEarlyEnabled(currentGame: Game, blockItemData: BlockItemData): Boolean {
        return currentGame.gameFinished.not() &&
                blockItemData.items.filterIsInstance<BlockResult>().any { it.total != null }
    }

    private suspend fun getCurrentGame(gameId: Long): Game? {
        return when (val result = getGameUseCase.invoke(gameId)) {
            is AppResult.Success -> result.value
            is AppResult.Error -> null
        }
    }

    private fun showTrumpSelectionDialog(data: BlockItemData) {
        val placeHolderItem =
            data.items.firstOrNull { it is BlockPlaceholder } as? BlockPlaceholder ?: return
        if (data.inputType == InputType.TIPP && placeHolderItem.trumpType == TrumpType.NONE) {
            viewModelScope.launch {
                when (val result = isShowTrumpDialogEnabledUseCase.invoke()) {
                    is AppResult.Success -> {
                        if (result.value) {
                            navigateTo(Screen.Block.Trump(TrumpType.NONE))
                        }
                    }
                    is AppResult.Error -> Unit
                }
            }
        }
    }

    fun onFabClicked() {
        val gameFinished = _gameFinished.value ?: false
        if (gameFinished) {
            navigateTo(Screen.Block.Exit)
        } else {
            val gameId = game.value?.gameInfo?.gameId ?: error("could not determine gameId")
            navigateTo(Screen.Block.Input(gameId))
        }
    }

    fun onTrophyClicked() {
        val scores = gameScores.value ?: return
        navigateTo(Screen.Block.Scores(scores))
    }

    private fun onGameFinished(results: List<GameScore>, onFinishedSuccess: (() -> Unit)? = null) {
        _showGameFinishedEvent.postValue(Unit)
        viewModelScope.launch {
            val gameId = game.value?.gameInfo?.gameId ?: return@launch
            when (val result = storeGameFinishedUseCase.invoke(gameId)) {
                is AppResult.Success -> {
                    navigateTo(Screen.Block.GameFinished(results.filter { it.position == WINNER_POSITION }))
                    onFinishedSuccess?.invoke()
                }
                is AppResult.Error -> Unit
            }
        }
    }

    override fun onTrumpClicked(trumpType: TrumpType) {
        navigateTo(Screen.Block.Trump(trumpType))
    }

    fun updateTrumpType(trumpType: TrumpType) {
        if (trumpType == TrumpType.NONE) return
        val game = game.value ?: error("round not initialized - could not set trump type")
        val currentRound = game.currentGameRound ?: error("could not get current round")
        val round = InsertRoundData(
            gameId = game.gameInfo.gameId,
            gameRound = currentRound.copy(
                trumpType = trumpType
            )
        )
        viewModelScope.launch {
            when (val result = storeRoundUseCase.invoke(round)) {
                is AppResult.Success -> setGameId(game.gameInfo.gameId)
                is AppResult.Error -> Unit
            }
        }
    }

    fun onInfoClicked() {
        navigateTo(Screen.Block.About)
    }

    fun onDeleteInputClicked() {
        val game = game.value ?: error("round not initialized - could not set trump type")
        val currentRound = game.currentGameRound ?: return

        viewModelScope.launch {
            navigateTo(Screen.Progress.Show(dim = true))

            val result = if (currentRound.playerTippData.isEmpty()) {
                val round = game.lastPlayedGameRound?.copy(
                    playerResultData = emptyList()
                ) ?: return@launch

                storeRoundUseCase.invoke(InsertRoundData(game.gameInfo.gameId, round))
            } else {
                removeRoundUseCase.invoke(DeleteRoundData(game.gameInfo.gameId, currentRound))
            }

            when (result) {
                is AppResult.Success -> setGameId(game.gameInfo.gameId)
                is AppResult.Error -> Unit
            }

            navigateTo(Screen.Progress.Hide)
        }
    }

    fun onFinishEarlyClicked() {
        navigateTo(Screen.Block.FinishEarly)
    }

    fun onFinishEarlyConfirmed() {
        val gameScoreData = gameScores.value ?: return
        _gameFinished.removeSource(gameScores)
        _finishEarlyEnabled.removeSource(blockItems)
        _finishEarlyEnabled.removeSource(gameFinished)
        _editInputEnabled.removeSource(blockItems)
        _editInputEnabled.removeSource(gameFinished)
        _gameFinished.postValue(true)
        onGameFinished(gameScoreData.results) {
            _editInputEnabled.postValue(false)
            _finishEarlyEnabled.postValue(false)
        }
    }

    fun showExitDialog() {
        navigateTo(Screen.Block.Exit)
    }
}
