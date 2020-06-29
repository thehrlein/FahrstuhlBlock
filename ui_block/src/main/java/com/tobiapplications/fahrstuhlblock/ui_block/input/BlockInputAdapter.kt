package com.tobiapplications.fahrstuhlblock.ui_block.input

import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputData
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.ItemBlockInputBinding
import com.tobiapplications.fahrstuhlblock.ui_common.extension.layoutInflater

class BlockInputAdapter : ListAdapter<InputData, BlockInputAdapter.BlockInputViewHolder>(
    BockInputDiff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockInputViewHolder {
        return BlockInputViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_block_input,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BlockInputViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BlockInputViewHolder(private val binding: ItemBlockInputBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(inputData: InputData) {
            binding.input.hint = inputData.player

            binding.input.addTextChangedListener {
                it?.toString()?.toIntOrNull()?.let { userInput ->
                    inputData.userInput = userInput
                }
            }
        }
    }
}

object BockInputDiff : DiffUtil.ItemCallback<InputData>() {

    override fun areItemsTheSame(oldItem: InputData, newItem: InputData): Boolean {
        return oldItem.player == newItem.player
    }

    override fun areContentsTheSame(oldItem: InputData, newItem: InputData): Boolean {
        return oldItem.player == newItem.player &&
                oldItem.cards == newItem.cards &&
                oldItem.currentRound == newItem.currentRound &&
                oldItem.type == newItem.type
    }
}