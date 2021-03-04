package com.tobiapplications.fahrstuhlblock.ui_block.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockPlaceholder
import com.tobiapplications.fahrstuhlblock.presentation.block.results.BlockResultsInteractions
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.ViewBlockPlaceholderBinding
import com.tobiapplications.fahrstuhlblock.ui_common.extension.executeAfter
import com.tobiapplications.fahrstuhlblock.ui_common.extension.layoutInflater

class BlockPlaceHolderView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding : ViewBlockPlaceholderBinding =
        DataBindingUtil.inflate(context.layoutInflater,
        R.layout.view_block_placeholder,
        this,
        true)


    fun setPlaceHolder(item: BlockPlaceholder, blockResultsInteractions: BlockResultsInteractions) {
        binding.executeAfter {
            this.item = item
        }

        binding.root.setOnClickListener {
            blockResultsInteractions.onTrumpClicked(item.trumpType)
        }
    }
}