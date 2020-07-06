package com.tobiapplications.fahrstuhlblock.ui_block.scores

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.GameScore
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.ItemBlockScoreBinding
import com.tobiapplications.fahrstuhlblock.ui_common.extension.executeAfter
import com.tobiapplications.fahrstuhlblock.ui_common.extension.layoutInflater

class BlockScoresAdapter : ListAdapter<GameScore, BlockScoresAdapter.BlockScoreViewHolder>(
    BlockScoreDiff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockScoreViewHolder {
        return BlockScoreViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_block_score, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BlockScoreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BlockScoreViewHolder(private val binding: ItemBlockScoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameScore) {
            binding.executeAfter {
                this.gameScore = item
            }
        }
    }
}

object BlockScoreDiff : DiffUtil.ItemCallback<GameScore>() {

    override fun areItemsTheSame(oldItem: GameScore, newItem: GameScore): Boolean =
        oldItem.player == newItem.player

    override fun areContentsTheSame(oldItem: GameScore, newItem: GameScore): Boolean =
        oldItem.points == newItem.points &&
                oldItem.position == newItem.position
}