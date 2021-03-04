package com.tobiapplications.fahrstuhlblock.presentation.savedgames

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.savedgames.SavedGameEntity
import com.tobiapplications.fahrstuhlblock.interactor.usecase.invoke
import com.tobiapplications.fahrstuhlblock.interactor.usecase.savedgames.DeleteSavedGameUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.savedgames.GetAllSavedGamesUseCase
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseToolbarViewModel
import kotlinx.coroutines.launch

class SavedGamesViewModel(
    private val getAllSavedGamesUseCase: GetAllSavedGamesUseCase,
    private val deleteSavedGameUseCase: DeleteSavedGameUseCase
) : BaseToolbarViewModel(), SavedGamesInteractions {

    private val _savedGames = MutableLiveData<List<SavedGameEntity>>()
    val savedGames: LiveData<List<SavedGameEntity>> = _savedGames
    private val _noSavedGamesEvent = MutableLiveData<Boolean>()
    val noSavedGames: LiveData<Boolean> = _noSavedGamesEvent

    init {
        getAllSavedGames()
    }

    private fun getAllSavedGames() {
        viewModelScope.launch {
            when (val result = getAllSavedGamesUseCase.invoke()) {
                is AppResult.Success -> convertGames(result.value)
                is AppResult.Error -> _noSavedGamesEvent.postValue(true)
            }
        }
    }

    private fun convertGames(games: List<Game>) {
        val continuableGames = games
            .map {
                SavedGameEntity(
                    gameId = it.gameInfo.gameId,
                    gameStartDate = it.gameInfo.gameStartDate,
                    players = it.gameInfo.players.names,
                    currentRound = it.currentRoundNumber,
                    maxRound = it.maxRound,
                    gameFinished = it.gameInfo.gameFinished
                )
            }
        _savedGames.postValue(continuableGames)
        _noSavedGamesEvent.postValue(continuableGames.isEmpty())
    }

    override fun onSavedGameClicked(gameId: Long) {
        navigateTo(Screen.SavedGames.ContinueGame(gameId))
    }

    fun onGameRemoved(item: SavedGameEntity?) {
        item?.gameId?.let {
            viewModelScope.launch {
                deleteSavedGameUseCase.invoke(it)
                getAllSavedGames()
            }
        }
    }
}
