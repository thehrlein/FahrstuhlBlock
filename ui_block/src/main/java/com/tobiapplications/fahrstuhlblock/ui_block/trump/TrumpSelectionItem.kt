package com.tobiapplications.fahrstuhlblock.ui_block.trump

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.Trump
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.WidgetTrumpSelectionItemBinding
import com.tobiapplications.fahrstuhlblock.ui_common.extension.executeAfter
import com.tobiapplications.fahrstuhlblock.ui_common.extension.layoutInflater

class TrumpSelectionItem @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetTrumpSelectionItemBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_trump_selection_item,
        this,
        true
    )

    fun setItem(trump: Trump, onItemClickListener: (TrumpType) -> Unit) {
        binding.executeAfter {
            this.item = trump
        }

        binding.root.setOnClickListener {
            onItemClickListener.invoke(trump.type)
            setChecked(true)
        }

        binding.radioButton.setOnClickListener {
            onItemClickListener.invoke(trump.type)
        }
    }

    fun setChecked(checked: Boolean) {
        binding.radioButton.isChecked = checked
    }

    fun isChecked(): Boolean {
        return binding.radioButton.isChecked
    }
}