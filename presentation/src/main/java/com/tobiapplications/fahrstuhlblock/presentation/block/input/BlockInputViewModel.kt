package com.tobiapplications.fahrstuhlblock.presentation.block.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.*
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.*
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.*
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

class BlockInputViewModel(
    private val gameId: Long,
    private val getGameUseCase: GetGameUseCase,
    private val calculateResultsUseCase: CalculateResultsUseCase,
    private val storeRoundUseCase: StoreRoundUseCase,
    private val inputsValidUseCase: InputsValidUseCase,
    private val getBlockInputModelsUseCase: GetBlockInputModelsUseCase
) : BaseViewModel(), BlockInputInteractions {

    private val _inputType = MutableLiveData(InputType.TIPP)
    val inputType: LiveData<InputType> = _inputType
    private val _inputModels = MutableLiveData<List<InputDataItem>>()
    val inputModelsItem: LiveData<List<InputDataItem>> = _inputModels
    private val _round = MutableLiveData<GameRound>()
    private val _game = MutableLiveData<Game>()
    val game: LiveData<Game> = _game
    private val _inputsValid = MutableLiveData(false)
    val inputsValid: LiveData<Boolean> = _inputsValid
    private val _summedInputs = MutableLiveData<Int>()
    val summedInputs: LiveData<Int> = _summedInputs

    init {
        getCurrentGame()
    }

    private fun getCurrentGame() {
        viewModelScope.launch {
            when (val result = getGameUseCase.invoke(gameId)) {
                is AppResult.Success -> setInputModels(result.value)
                is AppResult.Error -> Unit
            }
        }
    }

    private fun setInputModels(game: Game) {
        _game.postValue(game)
        viewModelScope.launch {
            when (val result = getBlockInputModelsUseCase.invoke(game)) {
                is AppResult.Success -> {
                    _round.postValue(result.value.currentGameRound)
                    _inputType.postValue(result.value.inputType)
                    _inputModels.postValue(result.value.inputModels)
                }
                is AppResult.Error -> Unit
            }
        }
    }

    fun onSaveClicked() {
        val currentRound = _round.value ?: error("could not determine round")
        val inputs = getInputs()
        if (currentRound.inputTypeForThisRound == InputType.TIPP) {
            storeTipps(currentRound, inputs)
        } else {
            calculateResults(currentRound, inputs)
        }
    }

    private fun getInputs() = _inputModels.value ?: error("could not determine input models")

    private fun storeTipps(
        currentGameRound: GameRound,
        inputItems: List<InputDataItem>
    ) {
        val round = InsertRoundData(gameId, currentGameRound.copy(
            playerTippData = inputItems.map {
                PlayerTippData(
                    playerName = it.player,
                    tipp = it.userInput
                )
            }
        ))

        viewModelScope.launch {
            when (val result = storeRoundUseCase.invoke(round)) {
                is AppResult.Success -> navigateTo(Screen.Input.Block)
                is AppResult.Error -> Unit
            }
        }
    }

    private fun calculateResults(currentGameRound: GameRound, inputItems: List<InputDataItem>) {
        val game = getGameData()
        val pointRulesData = game.gameInfo.pointsRuleData
        val previousTotals = game.previousTotals
        val calculateResultData = CalculateResultData(
            pointsRuleData = pointRulesData,
            resultData = inputItems.map { inputDataItem ->
                ResultData(
                    playerName = inputDataItem.player,
                    tipp = currentGameRound.playerTippData.first { it.playerName == inputDataItem.player }.tipp,
                    result = inputDataItem.userInput,
                    previousTotal = previousTotals.first { it.playerName == inputDataItem.player }.input
                )
            }
        )
        viewModelScope.launch {
            when (val result = calculateResultsUseCase.invoke(calculateResultData)) {
                is AppResult.Success -> storeResults(currentGameRound, result.value)
                is AppResult.Error -> Unit
            }
        }
    }

    private fun getGameData() = _game.value ?: error("could not determine game")

    private fun storeResults(currentGameRound: GameRound, results: List<PlayerResultData>) {
        val round = InsertRoundData(
            gameId, currentGameRound.copy(
                playerResultData = results
            )
        )

        viewModelScope.launch {
            when (val result = storeRoundUseCase.invoke(round)) {
                is AppResult.Success -> navigateTo(Screen.Input.Block)
                is AppResult.Error -> Unit
            }
        }
    }

    override fun onInputChanged() {
        val data = CheckInputValidityData(getGameData(), getInputs().sumBy { it.userInput })

        _summedInputs.postValue(data.inputSum)

        viewModelScope.launch {
            _inputsValid.postValue(when (val result = inputsValidUseCase.invoke(data)) {
                is AppResult.Success -> result.value
                is AppResult.Error -> false
            })
        }
    }

    fun onInfoIconClicked() {
        val game = _game.value ?: error("could not determine game")
        navigateTo(Screen.Input.Info(
            inputType = game.inputType,
            cardCount = game.currentCardCount,
            round = game.currentRoundNumber
        ))
    }
}
