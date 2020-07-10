package com.tobiapplications.fahrstuhlblock.presentation.model

import androidx.lifecycle.MutableLiveData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerError

data class PlayerNameData(
    val visible: Boolean,
    val input: MutableLiveData<String>,
    val error: PlayerError?
)