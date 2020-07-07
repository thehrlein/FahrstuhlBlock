package com.tobiapplications.fahrstuhlblock.presentation.block.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.*
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.GetBlockResultsUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.GetGameUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.GetGameScoresUseCase
import com.tobiapplications.fahrstuhlblock.presentation.SingleLiveEvent
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

private const val WINNER_POSITION = 1

class BlockResultsViewModel(
    private val getGameUseCase: GetGameUseCase,
    private val getBlockResultsUseCase: GetBlockResultsUseCase,
    private val getGameScoresUseCase: GetGameScoresUseCase
) : BaseViewModel() {

    private val _inputType = MutableLiveData<InputType>()
    val inputType: LiveData<InputType> = _inputType
    private val _blockItems = MutableLiveData<List<BlockItem>>()
    val blockItems: LiveData<List<BlockItem>> = _blockItems
    private val _openInputEvent = SingleLiveEvent<Unit>()
    val openInputEvent: LiveData<Unit> = _openInputEvent
    private val _openExitDialogEvent = SingleLiveEvent<Unit>()
    val openExitDialogEvent: LiveData<Unit> = _openExitDialogEvent
    private val _columnCount = MutableLiveData<Int>()
    val columnCount: LiveData<Int> = _columnCount
    private val game = MutableLiveData<Game>()

    val gameScores = MediatorLiveData<GameScoreData>().also { mediator ->
        mediator.addSource(game) {
            viewModelScope.launch {
                when (val result = getGameScoresUseCase.invoke(it)) {
                    is AppResult.Success -> mediator.postValue(result.value)
                    is AppResult.Error -> Unit
                }
            }
        }
    }

    fun setGameId(gameId: Long) {
        getCurrentGame(gameId)
        viewModelScope.launch {
            when (val result = getBlockResultsUseCase.invoke(gameId)) {
                is AppResult.Success -> {
                    _columnCount.postValue(result.value.columnCount)
                    _blockItems.postValue(result.value.items)
                    _inputType.postValue(result.value.inputType)
                }
                is AppResult.Error -> Unit
            }
        }
    }

    private fun getCurrentGame(gameId: Long) {
        viewModelScope.launch {
            when (val result = getGameUseCase.invoke(gameId)) {
                is AppResult.Success -> game.postValue(result.value)
                is AppResult.Error -> Unit
            }
        }
    }

    fun onFabClicked() {
        val gameFinished = gameScores.value?.finished ?: false
        if (gameFinished) {
            _openExitDialogEvent.call()
        } else {
            _openInputEvent.call()
        }
    }

    fun onTrophyClicked() {
        val scores = gameScores.value ?: return
        navigateTo(Screen.Block.Scores(scores))
    }

    fun onGameFinished(results: List<GameScore>) {
        navigateTo(Screen.Block.GameFinished(results.filter { it.position == WINNER_POSITION }))
    }

}