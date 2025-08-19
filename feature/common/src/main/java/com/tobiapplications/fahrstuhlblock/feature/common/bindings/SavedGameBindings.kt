package com.tobiapplications.fahrstuhlblock.feature.common.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.savedgames.SavedGameEntity
import com.tobiapplications.fahrstuhlblock.feature.common.R
import com.tobiapplications.fahrstuhlblock.feature.common.utils.DateUtils

@BindingAdapter("playerNames")
fun TextView.setPlayerNames(playerNames: List<String>) {
    text = playerNames.joinToString { it }
}

@BindingAdapter("round")
fun TextView.setRound(item: SavedGameEntity) {
    text = if (item.gameFinished) context.getString(com.tobiapplications.fahrstuhlblock.feature.common.R.string.saved_game_finished) else context.getString(com.tobiapplications.fahrstuhlblock.feature.common.R.string.saved_game_current_max_round, item.currentRound, item.maxRound)
}

@BindingAdapter("gameStartDate")
fun TextView.setGameStartDate(gameStartDate: Long) {
    text = DateUtils.getGameStartDate(gameStartDate)
}
