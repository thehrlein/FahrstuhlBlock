package com.tobiapplications.fahrstuhlblock.ui_common.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.ui_common.R

@BindingAdapter("resultText")
fun TextView.setResultText(value: Int?) {
    text = value?.let {
        it.toString()
    }
}

@BindingAdapter("inputTitle")
fun TextView.setInputTitle(game: Game?) {
    if (game?.currentRound == null) return
    text = context.getString(R.string.block_input_general_title, game.currentRound, game.currentCardCount)
}

@BindingAdapter("inputMessage", "game", requireAll = true)
fun TextView.setInputMessage(inputType: InputType?, game: Game?) {
    if (inputType == null || game?.currentCardCount == null) return
    text = when (inputType) {
        InputType.TIPP -> context.getString(R.string.block_input_tipps_message, game.currentCardCount)
        InputType.RESULT -> context.getString(R.string.block_input_result_message, game.currentCardCount)
    }
}