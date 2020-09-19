package com.tobiapplications.fahrstuhlblock.ui_block.trump

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.Trump
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.WidgetTrumpSelectionGroupBinding

class TrumpSelectionGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetTrumpSelectionGroupBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.widget_trump_selection_group,
        this,
        true
    )
    private val items = mutableMapOf<TrumpType, TrumpSelectionItem>()
    private var onCheckedChange: ((TrumpType) -> Unit)? = null

    init {
        setItems()
    }

    private fun setItems() {
        binding.groupOne.removeAllViews()
        binding.groupTwo.removeAllViews()

        binding.groupOne.addView(TrumpSelectionItem(context).apply {
            setItem(Trump(context.getString(R.string.block_trump_type_clubs), TrumpType.CLUB)) {
                setTrumpSelected(it)
            }
        }.also {
            items[TrumpType.CLUB] = it
        })
        binding.groupOne.addView(TrumpSelectionItem(context).apply {
            setItem(Trump(context.getString(R.string.block_trump_type_spades), TrumpType.SPADE)) {
                setTrumpSelected(it)
            }
        }.also {
            items[TrumpType.SPADE] = it
        })
        binding.groupTwo.addView(TrumpSelectionItem(context).apply {
            setItem(Trump(context.getString(R.string.block_trump_type_hearts), TrumpType.HEART)) {
                setTrumpSelected(it)
            }
        }.also {
            items[TrumpType.HEART] = it
        })
        binding.groupTwo.addView(TrumpSelectionItem(context).apply {
            setItem(
                Trump(
                    context.getString(R.string.block_trump_type_diamonds),
                    TrumpType.DIAMOND
                )
            ) {
                setTrumpSelected(it)
            }
        }.also {
            items[TrumpType.DIAMOND] = it
        })
    }

    private fun setTrumpSelected(trumpType: TrumpType) {
        for ((type, item) in items) {
            if (type != trumpType) {
                item.setChecked(false)
            }
        }
        onCheckedChange?.invoke(trumpType)
    }

    fun getSelectedTrumpType(): TrumpType {
        val item = items.values.firstOrNull { it.isChecked() }
        return items.filterValues { it == item }.keys.firstOrNull() ?: TrumpType.NONE
    }

    fun setSelectedItem(selectedTrumpType: TrumpType) {
//        items[selectedTrumpType]?.setChecked(true)
        items.forEach {
            it.value.setChecked(it.key == selectedTrumpType)
        }
    }

    fun setOnCheckedChangeListener(onCheckedChange: (TrumpType) -> Unit) {
        this.onCheckedChange = onCheckedChange
    }
}
