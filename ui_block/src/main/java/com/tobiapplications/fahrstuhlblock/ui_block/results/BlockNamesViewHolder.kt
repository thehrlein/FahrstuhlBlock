package com.tobiapplications.fahrstuhlblock.ui_block.results

import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockNames
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.ItemBlockNamesBinding
import com.tobiapplications.fahrstuhlblock.ui_common.extension.getDimen

class BlockNamesViewHolder(
    private val binding: ItemBlockNamesBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BlockNames) {
        binding.headerNamesLayout.removeAllViews()

        item.names.forEachIndexed { index, name ->
            addPlayerTextView(index, name)
        }
    }

    private fun addPlayerTextView(index: Int, name: String) {
        val textView = TextView(binding.root.context)
        textView.text = name
        textView.gravity = Gravity.CENTER
        val cellWidth = binding.root.context.getDimen(R.dimen.block_cell_width)
        val cellHeigth = binding.root.context.getDimen(R.dimen.block_cell_height)
        textView.layoutParams = LinearLayout.LayoutParams(cellWidth, cellHeigth)
        textView.setBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                if (index % 2 == 0) R.color.color_primary else R.color.color_secondary
            )
        )

        binding.headerNamesLayout.addView(textView)
    }
}