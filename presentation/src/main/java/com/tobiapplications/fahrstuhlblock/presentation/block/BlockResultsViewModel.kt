package com.tobiapplications.fahrstuhlblock.presentation.block

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockNames
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.GetGameUseCase
import com.tobiapplications.fahrstuhlblock.presentation.SingleLiveEvent
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

class BlockResultsViewModel(
    private val getGameUseCase: GetGameUseCase
) : BaseViewModel() {

    private val _names = MutableLiveData<BlockNames>()
    val names: LiveData<BlockNames> = _names
    private val _openInputEvent = SingleLiveEvent<Unit>()
    val openInputEvent: LiveData<Unit> = _openInputEvent

    fun setGameId(it: Long) {
        viewModelScope.launch {
            when (val result = getGameUseCase.invoke(it)) {
                is AppResult.Success -> _names.postValue(
                    BlockNames(
                        result.value.players.names
                    )
                )
                is AppResult.Error -> Unit
            }
        }
    }

    fun onFabClicked() {
        _openInputEvent.call()
    }

}