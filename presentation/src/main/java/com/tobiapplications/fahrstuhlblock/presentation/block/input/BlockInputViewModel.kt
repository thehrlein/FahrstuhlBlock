package com.tobiapplications.fahrstuhlblock.presentation.block.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.*
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CheckInputValidityData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.CalculateResultsUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.GetGameUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.InputsValidUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.StoreRoundUseCase
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

private const val DEFAULT_PLAYER_INPUT = 0

class BlockInputViewModel(
    private val gameId: Long,
    private val getGameUseCase: GetGameUseCase,
    private val calculateResultsUseCase: CalculateResultsUseCase,
    private val storeRoundUseCase: StoreRoundUseCase,
    private val inputsValidUseCase: InputsValidUseCase
) : BaseViewModel(), BlockInputInteractions {

    private val _inputType = MutableLiveData(InputType.TIPP)
    val inputType: LiveData<InputType> = _inputType
    private val _inputModels = MutableLiveData<List<InputData>>()
    val inputModels: LiveData<List<InputData>> = _inputModels
    private val _round = MutableLiveData<Round>()
    private val _game = MutableLiveData<Game>()
    val game: LiveData<Game> = _game
    private val _inputsValid = MutableLiveData(false)
    val inputsValid: LiveData<Boolean> = _inputsValid

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
        val round = game.currentRound
        _round.postValue(round)
        val inputType = round.currentInputType
        _inputType.postValue(inputType)
        _inputModels.postValue(game.gameInfo.players.names.mapIndexed { index: Int, name: String ->
            InputData(
                type = inputType,
                player = name,
                currentRound = game.currentRoundNumber,
                cards = game.currentCardCount,
                userInput = round.playerTippData.getOrNull(index)?.tipp ?: DEFAULT_PLAYER_INPUT
            )
        })
    }

    private fun createNewRound(card: Int): Round {
        return Round(card, emptyList(), emptyList(), TrumpType.NONE)
    }

    fun onSaveClicked() {
        val currentRound = _round.value ?: error("could not determine round")
        val inputs = getInputs()
        if (currentRound.currentInputType == InputType.TIPP) {
            storeTipps(currentRound, inputs)
        } else {
            calculateResults(currentRound, inputs)
        }
    }

    private fun getInputs() = _inputModels.value ?: error("could not determine input models")

    private fun storeTipps(
        currentRound: Round,
        inputs: List<InputData>
    ) {
        val round = InsertRoundData(gameId, currentRound.copy(
            playerTippData = inputs.map { PlayerTippData(it.userInput) }
        ))

        viewModelScope.launch {
            when (val result = storeRoundUseCase.invoke(round)) {
                is AppResult.Success -> navigateTo(Screen.Input.Block)
                is AppResult.Error -> Unit
            }
        }
    }

    private fun calculateResults(currentRound: Round, inputs: List<InputData>) {
        val game = getGameData()
        val pointRulesData = game.gameInfo.pointsRuleData
        val previousTotals = game.previousTotals
        val calculateResultData = CalculateResultData(
            pointsRuleData = pointRulesData,
            tipps = currentRound.playerTippData.map { it.tipp },
            results = inputs.map { it.userInput },
            previousTotals = previousTotals
        )
        viewModelScope.launch {
            when (val result = calculateResultsUseCase.invoke(calculateResultData)) {
                is AppResult.Success -> storeResults(currentRound, result.value)
                is AppResult.Error -> Unit
            }
        }
    }

    private fun getGameData() = _game.value ?: error("could not determine game")

    private fun storeResults(currentRound: Round, results: List<PlayerResultData>) {
        val round = InsertRoundData(
            gameId, currentRound.copy(
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

        viewModelScope.launch {
            _inputsValid.postValue(when (val result = inputsValidUseCase.invoke(data)) {
                is AppResult.Success -> result.value
                is AppResult.Error -> false
            })
        }
    }
}