package com.tobiapplications.fahrstuhlblock.feature.block.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiapplications.fahrstuhlblock.core.entities.models.game.result.BlockName
import com.tobiapplications.fahrstuhlblock.feature.block.R
import com.tobiapplications.fahrstuhlblock.feature.block.databinding.ViewBlockNameBinding
import com.tobiapplications.fahrstuhlblock.feature.common.extension.executeAfter
import com.tobiapplications.fahrstuhlblock.feature.common.extension.layoutInflater

class BlockNameView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: ViewBlockNameBinding =
        DataBindingUtil.inflate(context.layoutInflater,
        R.layout.view_block_name,
        this,
        true)

    fun setName(item: BlockName) {
        binding.executeAfter {
            this.item = item
        }
    }
}
