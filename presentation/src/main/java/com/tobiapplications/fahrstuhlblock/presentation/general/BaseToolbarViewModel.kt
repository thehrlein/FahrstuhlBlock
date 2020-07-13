package com.tobiapplications.fahrstuhlblock.presentation.general

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tobiapplications.fahrstuhlblock.entities.general.toolbar.ToolbarButtonType
import com.tobiapplications.fahrstuhlblock.presentation.SingleLiveEvent

abstract class BaseToolbarViewModel : BaseViewModel() {

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String> = _toolbarTitle
    private val _toolbarButton = MutableLiveData<ToolbarButtonType>(ToolbarButtonType.None)
    val toolbarButton: LiveData<ToolbarButtonType> = _toolbarButton
    private val _toolbarEvent = SingleLiveEvent<Unit>()
    val toolbarEvent: LiveData<Unit> = _toolbarEvent

    fun toolBarButtonClicked(){
        _toolbarEvent.call()
    }

    fun setTitle(title: String) {
        _toolbarTitle.postValue(title)
    }

    fun setToolbarButton(toolbarButtonType: ToolbarButtonType) {
        _toolbarButton.postValue(toolbarButtonType)
    }

}
