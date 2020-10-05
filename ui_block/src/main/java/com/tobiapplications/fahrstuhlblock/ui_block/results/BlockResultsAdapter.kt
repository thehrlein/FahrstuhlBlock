package com.tobiapplications.fahrstuhlblock.ui_block.results

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.*
import com.tobiapplications.fahrstuhlblock.presentation.block.results.BlockResultsInteractions
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.ItemBlockNameBinding
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.ItemBlockPlaceholderBinding
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.ItemBlockResultBinding
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.ItemBlockRoundBinding
import com.tobiapplications.fahrstuhlblock.ui_common.extension.executeAfter
import com.tobiapplications.fahrstuhlblock.ui_common.extension.layoutInflater

class BlockResultsAdapter(
    private val interactions: BlockResultsInteractions
) : ListAdapter<BlockItem, RecyclerView.ViewHolder>(
    BlockResultsDiff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = parent.context.layoutInflater

        return when (viewType) {
            R.layout.item_block_placeholder -> {
                BlockPlaceHolderViewHolder(
                    DataBindingUtil.inflate(inflater, viewType, parent, false)
                )
            }
            R.layout.item_block_name -> {
                BlockNameViewHolder(
                    DataBindingUtil.inflate(inflater, viewType, parent, false)
                )
            }
            R.layout.item_block_round -> {
                BlockRoundViewHolder(
                    DataBindingUtil.inflate(inflater, viewType, parent, false)
                )
            }
            R.layout.item_block_result -> {
                BlockResultViewHolder(
                    DataBindingUtil.inflate(inflater, viewType, parent, false)
                )
            }
            else -> throw IllegalStateException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is BlockPlaceholder -> R.layout.item_block_placeholder
            is BlockName -> R.layout.item_block_name
            is BlockRound -> R.layout.item_block_round
            is BlockResult -> R.layout.item_block_result
            else -> throw IllegalStateException("Unknown item type: $item")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is BlockPlaceHolderViewHolder -> {
                if (item is BlockPlaceholder) {
                    holder.bind(
                        item = item,
                        interactions = interactions
                    )
                }
            }
            is BlockNameViewHolder -> {
                if (item is BlockName) {
                    holder.bind(item)
                }
            }
            is BlockRoundViewHolder -> {
                if (item is BlockRound) {
                    holder.bind(item)
                }
            }
            is BlockResultViewHolder -> {
                if (item is BlockResult) {
                    holder.bind(item)
                }
            }
        }
    }

    inner class BlockPlaceHolderViewHolder(private val binding: ItemBlockPlaceholderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: BlockPlaceholder,
            interactions: BlockResultsInteractions
        ) {
            binding.executeAfter {
                this.item = item
            }

            itemView.setOnClickListener {
                interactions.onTrumpClicked(item.trumpType)
            }
        }
    }

    inner class BlockNameViewHolder(private val binding: ItemBlockNameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BlockName) {
            binding.executeAfter {
                this.item = item
            }
        }
    }

    inner class BlockResultViewHolder(private val binding: ItemBlockResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BlockResult) {
            binding.executeAfter {
                this.item = item
            }
        }
    }

    inner class BlockRoundViewHolder(private val binding: ItemBlockRoundBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BlockRound) {
            binding.executeAfter {
                this.item = item
            }
        }
    }
}

object BlockResultsDiff : DiffUtil.ItemCallback<BlockItem>() {

    override fun areItemsTheSame(oldItem: BlockItem, newItem: BlockItem) = when {
        oldItem is BlockPlaceholder && newItem is BlockPlaceholder -> true
        oldItem is BlockName && newItem is BlockName && oldItem.name == newItem.name -> true
        oldItem is BlockRound && newItem is BlockRound && oldItem.round == newItem.round -> true
        oldItem is BlockResult && newItem is BlockResult && oldItem.round == newItem.round && oldItem.player == newItem.player -> true
        else -> false
    }

    override fun areContentsTheSame(oldItem: BlockItem, newItem: BlockItem) = when {
        oldItem is BlockPlaceholder && newItem is BlockPlaceholder && oldItem.trumpType == newItem.trumpType -> true
        oldItem is BlockName && newItem is BlockName && oldItem.name == newItem.name &&
                oldItem.isDealer == newItem.isDealer &&
                oldItem.isCurrentLeader == newItem.isCurrentLeader -> true
        oldItem is BlockRound && newItem is BlockRound && oldItem.round == newItem.round -> true
        oldItem is BlockResult && newItem is BlockResult &&
                oldItem.round == newItem.round &&
                oldItem.player == newItem.player &&
                oldItem.tipp == newItem.tipp &&
                oldItem.result == newItem.result &&
                oldItem.difference == newItem.difference &&
                oldItem.total == newItem.total -> true
        else -> false
    }
}
