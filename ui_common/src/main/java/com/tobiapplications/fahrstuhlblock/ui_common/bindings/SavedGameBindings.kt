package com.tobiapplications.fahrstuhlblock.ui_common.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tobiapplications.fahrstuhlblock.entities.models.game.savedgames.SavedGameEntity
import com.tobiapplications.fahrstuhlblock.ui_common.R
import com.tobiapplications.fahrstuhlblock.ui_common.utils.DateUtils

@BindingAdapter("playerNames")
fun TextView.setPlayerNames(playerNames: List<String>) {
    text = playerNames.joinToString { it }
}

@BindingAdapter("round")
fun TextView.setRound(item: SavedGameEntity) {
    text = context.getString(R.string.saved_game_current_max_round, item.currentRound, item.maxRound)
}

@BindingAdapter("gameStartDate")
fun TextView.setGameStartDate(gameStartDate: Long) {
    text = DateUtils.getGameStartDate(gameStartDate)
}