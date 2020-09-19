package com.tobiapplications.fahrstuhlblock.ui_game_settings.playersettings

import androidx.databinding.BindingAdapter
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerError
import com.tobiapplications.fahrstuhlblock.ui_game_settings.R

@BindingAdapter("playerError")
fun PlayerNameInputView.setPlayerError(errorType: PlayerError?) {
    setError(when (errorType) {
        PlayerError.EMPTY -> context.getString(R.string.player_settings_player_name_empty)
        PlayerError.DUPLICATE -> context.getString(R.string.player_settings_player_name_duplicate)
        else -> null
    })
}
