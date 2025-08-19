package com.tobiapplications.fahrstuhlblock.feature.savedgames

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.savedgames.SavedGameEntity
import com.tobiapplications.fahrstuhlblock.core.presentation.savedgames.SavedGamesInteractions
import com.tobiapplications.fahrstuhlblock.feature.common.extension.executeAfter
import com.tobiapplications.fahrstuhlblock.feature.common.extension.layoutInflater
import com.tobiapplications.fahrstuhlblock.feature.savedgames.databinding.ItemSavedGameBinding

class SavedGamesAdapter(
    private val savedGamesInteractions: SavedGamesInteractions
) : ListAdapter<SavedGameEntity, SavedGamesAdapter.SavedGameViewHolder>(
    SavedGameDiff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedGameViewHolder {
        return SavedGameViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_saved_game,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SavedGameViewHolder, position: Int) {
        holder.bind(getItem(position), savedGamesInteractions)
    }

    inner class SavedGameViewHolder(val binding: ItemSavedGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: SavedGameEntity,
            interactions: SavedGamesInteractions
        ) {
            binding.executeAfter {
                this.item = item
            }

            binding.root.setOnClickListener {
                interactions.onSavedGameClicked(item.gameId)
            }
        }
    }
}

object SavedGameDiff : DiffUtil.ItemCallback<SavedGameEntity>() {

    override fun areItemsTheSame(oldItem: SavedGameEntity, newItem: SavedGameEntity): Boolean =
        oldItem.gameId == newItem.gameId

    override fun areContentsTheSame(oldItem: SavedGameEntity, newItem: SavedGameEntity): Boolean =
        oldItem == newItem
}
