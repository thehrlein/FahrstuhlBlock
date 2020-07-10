package com.tobiapplications.fahrstuhlblock.ui_common.bindings

import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerError
import com.tobiapplications.fahrstuhlblock.ui_common.R
import com.tobiapplications.fahrstuhlblock.ui_common.views.PlayerNameInputView

@BindingAdapter("player")
fun PlayerNameInputView.setPlayer(newText: String?) {
    if (newText == getPlayerName()) return
    setPlayerName(newText)
    requestLayout()
}

@BindingAdapter("playerAttrChanged")
fun PlayerNameInputView.setPlayerAttrChangedListener(listener: InverseBindingListener?) {
    listener?.let {
        addTextChangeListener {
            listener.onChange()
        }
    }
}

@InverseBindingAdapter(attribute = "player")
fun PlayerNameInputView.getPlayer(): String? {
    return getPlayerName()
}

@BindingAdapter("adapterItems")
fun PlayerNameInputView.setAdapterItems(items: Set<String>?) {
    items?.let {
        setAdapter(it)
    }
}

@BindingAdapter("playerError")
fun PlayerNameInputView.setPlayerError(errorType: PlayerError?) {
    setError(when (errorType) {
        PlayerError.EMPTY -> context.getString(R.string.player_settings_player_name_empty)
        PlayerError.DUPLICATE -> context.getString(R.string.player_settings_player_name_duplicate)
        else -> null
    })
}