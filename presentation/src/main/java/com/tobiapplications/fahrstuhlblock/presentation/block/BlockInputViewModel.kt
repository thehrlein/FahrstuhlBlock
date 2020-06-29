package com.tobiapplications.fahrstuhlblock.presentation.block

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.*
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.CalculateResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputData
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.CalculateResultsUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.GetGameUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.StoreRoundUseCase
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

class BlockInputViewModel(
    private val gameId: Long,
    private val getGameUseCase: GetGameUseCase,
    private val calculateResultsUseCase: CalculateResultsUseCase,
    private val storeRoundUseCase: StoreRoundUseCase
) : BaseViewModel() {

    private val _inputModels = MutableLiveData<List<InputData>>()
    val inputModels: LiveData<List<InputData>> = _inputModels
    private val _round = MutableLiveData<Round>()
    private var _game = MutableLiveData<Game>()

    init {
        getGame()
    }

    private fun getGame() {
        viewModelScope.launch {
            when (val result = getGameUseCase.invoke(gameId)) {
                is AppResult.Success -> setInputModels(result.value)
                is AppResult.Error -> Unit
            }
        }
    }

    private fun setInputModels(game: Game) {
        _game.postValue(game)
        _round.postValue(game.rounds.lastOrNull() ?: Round(1, emptyList(), emptyList()))
        val inputType = game.rounds.lastOrNull()?.currentInputType() ?: InputType.TIPP
        val players = game.players.names
        val currentRound = game.rounds.size
        val cards = game.currentCardCount()
        _inputModels.postValue(players.map {
            InputData(
                type = inputType,
                player = it,
                currentRound = currentRound,
                cards = cards
            )
        })
    }

    fun onSaveClicked() {
        val currentRound = _round.value ?: error("could not determine round")
        val inputs = _inputModels.value ?: error("could not determine input models")
        if (currentRound.playerTippData.isNullOrEmpty()) {
            storeTipps(currentRound, inputs)
        } else {
            calculateResults(currentRound, inputs)
        }
    }

    private fun storeTipps(
        currentRound: Round,
        inputs: List<InputData>
    ) {
        val round = InsertRoundData(gameId, currentRound.copy(
            playerTippData = inputs.map { PlayerTippData(it.userInput) }
        ))

        viewModelScope.launch {
            when (val result = storeRoundUseCase.invoke(round)) {
                is AppResult.Success -> Unit
                is AppResult.Error -> Unit
            }
        }
    }

    private fun calculateResults(currentRound: Round, inputs: List<InputData>) {
        val game = _game.value ?:error("could not determine game")
        val pointRulesData = game.pointsRuleData
        val previousTotals: List<Int> = if (game.rounds.isEmpty()) inputs.map { 0 } else game.rounds[game.rounds.size - 1].playerResultData.map { it.total }
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

    private fun storeResults(currentRound: Round, results: List<PlayerResultData>) {
        val round = InsertRoundData(gameId, currentRound.copy(
            playerResultData = results
        ))

        viewModelScope.launch {
            when (val result = storeRoundUseCase.invoke(round)) {
                is AppResult.Success -> Unit
                is AppResult.Error -> Unit
            }
        }
    }

}