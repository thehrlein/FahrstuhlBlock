package com.tobiapplications.fahrstuhlblock.feature.gamesettings.playersettings

import androidx.databinding.BindingAdapter
import com.tobiapplications.fahrstuhlblock.core.entities.models.settings.PlayerError
import com.tobiapplications.fahrstuhlblock.feature.gamesettings.R

@BindingAdapter("playerError")
fun PlayerNameInputView.setPlayerError(errorType: PlayerError?) {
    setError(when (errorType) {
        PlayerError.EMPTY -> context.getString(com.tobiapplications.fahrstuhlblock.feature.common.R.string.player_settings_player_name_empty)
        PlayerError.DUPLICATE -> context.getString(com.tobiapplications.fahrstuhlblock.feature.common.R.string.player_settings_player_name_duplicate)
        else -> null
    })
}
