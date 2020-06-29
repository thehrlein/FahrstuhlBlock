package com.tobiapplications.fahrstuhlblock.ui_block.results

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockItem
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockNames
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockRound
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_common.extension.layoutInflater

class BlockResultAdapter : ListAdapter<BlockItem, RecyclerView.ViewHolder>(
    BlockDiff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = parent.context.layoutInflater

        return when (viewType) {
            R.layout.item_block_names -> {
                BlockNamesViewHolder(
                    DataBindingUtil.inflate(inflater, viewType, parent, false)
                )
            }
            R.layout.item_block_round -> {
                BlockRoundViewHolder(
                    DataBindingUtil.inflate(inflater, viewType, parent, false)
                )
            }
            else -> throw IllegalStateException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is BlockNamesViewHolder -> {
                if (item is BlockNames) {
                    holder.bind(
                        item = item
                    )
                }
            }
            is BlockRoundViewHolder -> {
                if (item is BlockRound) {
                    holder.bind(item)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is BlockNames -> R.layout.item_block_names
            is BlockRound -> R.layout.item_block_round
            else -> throw IllegalStateException("Unknown item type: $item")
        }
    }

    override fun getItemId(position: Int): Long {
        val item = getItem(position)

//        if (item is Trip) {
//            item.tripId.toLong()
//        } else {
        return super.getItemId(position)
//        }
    }
}

object BlockDiff : DiffUtil.ItemCallback<BlockItem>() {

    override fun areItemsTheSame(oldItem: BlockItem, newItem: BlockItem) = when {
        oldItem is BlockNames && newItem is BlockNames -> true
        else -> false
    }

    override fun areContentsTheSame(oldItem: BlockItem, newItem: BlockItem) = when {
        oldItem is BlockNames && newItem is BlockNames && oldItem.names == newItem.names -> true
        else -> false
    }
}