package com.tobiapplications.fahrstuhlblock.presentation.block.trump

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiapplications.fahrstuhlblock.entities.general.AppResult
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.AnalyticsEvent
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.BooleanParam
import com.tobiapplications.fahrstuhlblock.entities.models.firebase.TrackingConstants
import com.tobiapplications.fahrstuhlblock.interactor.usecase.firebase.TrackAnalyticsEventUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.invoke
import com.tobiapplications.fahrstuhlblock.interactor.usecase.user.IsShowTrumpDialogEnabledUseCase
import com.tobiapplications.fahrstuhlblock.interactor.usecase.user.SetShowTrumpDialogEnabledUseCase
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import kotlinx.coroutines.launch

class BlockTrumpViewModel(
    private val isShowTrumpDialogEnabledUseCase: IsShowTrumpDialogEnabledUseCase,
    private val setShowTrumpDialogEnabledUseCase: SetShowTrumpDialogEnabledUseCase,
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase
) : BaseViewModel() {

    private val _showTrumpDialogEnabled = MutableLiveData<Boolean>()
    val showTrumpDialogEnabled: LiveData<Boolean> = _showTrumpDialogEnabled

    init {
        checkShowTrumpDialogEnabled()
    }

    private fun checkShowTrumpDialogEnabled() {
        viewModelScope.launch {
            when (val result = isShowTrumpDialogEnabledUseCase.invoke()) {
                is AppResult.Success -> _showTrumpDialogEnabled.postValue(result.value)
                is AppResult.Error -> Unit
            }
        }
    }

    fun onAutoShowTrumpDialogChanged(checked: Boolean) {
        if (checked == showTrumpDialogEnabled.value) return
        viewModelScope.launch {
            trackAnalyticsEventUseCase.invoke(
                AnalyticsEvent(
                    eventName = TrackingConstants.EVENT_TRUMP_SELECTION_AUTO_SHOW_DIALOG,
                    params = listOf(BooleanParam(TrackingConstants.PARAM_AUTO_SHOW_DIALOG, checked))
                )
            )
            when (val result = setShowTrumpDialogEnabledUseCase.invoke(checked)) {
                is AppResult.Success -> Unit
                is AppResult.Error -> Unit
            }
        }
    }
}