package com.tobiapplications.fahrstuhlblock.presentation.block

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.*
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.GetBlockResultsUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.block.GetGameUseCase
import com.tobiapplications.fahrstuhlblock.presentation.SingleLiveEvent
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

class BlockResultsViewModel(
    private val getBlockResultsUseCase: GetBlockResultsUseCase
) : BaseViewModel() {

    private val _inputType = MutableLiveData<InputType>()
    val inputType: LiveData<InputType> = _inputType
    private val _blockItems = MutableLiveData<List<BlockItem>>()
    val blockItems: LiveData<List<BlockItem>> = _blockItems
    private val _openInputEvent = SingleLiveEvent<Unit>()
    val openInputEvent: LiveData<Unit> = _openInputEvent
    private val _columnCount = MutableLiveData<Int>()
    val columnCount: LiveData<Int> = _columnCount

    fun setGameId(it: Long) {
        viewModelScope.launch {
            when (val result = getBlockResultsUseCase.invoke(it)) {
                is AppResult.Success -> {
                    _columnCount.postValue(result.value.columnCount)
                    _blockItems.postValue(result.value.items)
                    _inputType.postValue(result.value.inputType)
                }
                is AppResult.Error -> Unit
            }
        }
    }

    fun onFabClicked() {
        _openInputEvent.call()
    }

}