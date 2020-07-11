package com.tobiapplications.fahrstuhlblock.ui_common.bindings

import android.graphics.Typeface
import android.widget.TextView
import androidx.core.content.ContextCompat
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

@BindingAdapter("differenceText")
fun TextView.setDifferenceText(value: Int?) {
    text = value?.let {
        setTextColor(ContextCompat.getColor(context, if (it > 0)  R.color.block_result_positive_difference else R.color.block_result_negative_difference))
        it.toString()
    }
}

@BindingAdapter("inputTitle")
fun TextView.setInputTitle(game: Game?) {
    if (game?.currentRoundNumber == null) return
    text = context.getString(R.string.block_input_general_title, game.currentRoundNumber, game.currentCardCount)
}

@BindingAdapter("inputMessage", "game", requireAll = true)
fun TextView.setInputMessage(inputType: InputType?, game: Game?) {
    if (inputType == null || game?.currentCardCount == null) return
    setVisible(game.currentRoundNumber > 1 || inputType == InputType.RESULT)
    text = when (inputType) {
        InputType.TIPP -> context.getString(R.string.block_input_tipps_message, game.currentCardCount)
        InputType.RESULT -> context.getString(R.string.block_input_result_message, game.currentCardCount)
    }
}

@BindingAdapter("position")
fun TextView.setPosition(pos: Int?) {
    if (pos == null) return
    text = context.getString(R.string.block_scores_position, pos)
}

@BindingAdapter("bold")
fun TextView.setBold(bold: Boolean?) {
    setTypeface(null, if (bold == true) Typeface.BOLD else Typeface.NORMAL)
}