package com.tobiapplications.fahrstuhlblock.ui_block.input

import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputDataItem
import com.tobiapplications.fahrstuhlblock.presentation.block.input.BlockInputInteractions
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.ItemBlockInputBinding
import com.tobiapplications.fahrstuhlblock.ui_common.extension.executeAfter
import com.tobiapplications.fahrstuhlblock.ui_common.extension.layoutInflater

class BlockInputAdapter(
    private val interactions: BlockInputInteractions
) : ListAdapter<InputDataItem, BlockInputAdapter.BlockInputViewHolder>(
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
        holder.bind(getItem(position), interactions)
    }

    inner class BlockInputViewHolder(private val binding: ItemBlockInputBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            inputDataItem: InputDataItem,
            interactions: BlockInputInteractions
        ) {
            bindInputData(inputDataItem, interactions)
            binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    inputDataItem.userInput = progress
                    bindInputData(inputDataItem, interactions)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // not needed
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // not needed
                }
            })

            binding.buttonDecrease.setOnClickListener {
                if (inputDataItem.userInput > 0) {
                    inputDataItem.userInput = inputDataItem.userInput - 1
                    bindInputData(inputDataItem, interactions)
                }
            }

            binding.buttonIncrease.setOnClickListener {
                if (inputDataItem.userInput < inputDataItem.cards) {
                    inputDataItem.userInput = inputDataItem.userInput + 1
                    bindInputData(inputDataItem, interactions)
                }
            }
        }

        fun bindInputData(inputDataItem: InputDataItem, interactions: BlockInputInteractions) {
            binding.executeAfter {
                this.item = inputDataItem
            }
            interactions.onInputChanged()
        }
    }
}

object BockInputDiff : DiffUtil.ItemCallback<InputDataItem>() {

    override fun areItemsTheSame(oldItem: InputDataItem, newItem: InputDataItem): Boolean {
        return oldItem.player == newItem.player
    }

    override fun areContentsTheSame(oldItem: InputDataItem, newItem: InputDataItem): Boolean {
        return oldItem.player == newItem.player &&
                oldItem.cards == newItem.cards &&
                oldItem.currentRound == newItem.currentRound &&
                oldItem.type == newItem.type
    }
}
