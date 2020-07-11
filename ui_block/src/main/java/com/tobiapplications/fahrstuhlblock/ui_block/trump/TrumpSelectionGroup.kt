package com.tobiapplications.fahrstuhlblock.ui_block.trump

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.Trump
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import com.tobiapplications.fahrstuhlblock.ui_block.R

class TrumpSelectionGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val items = mutableMapOf<TrumpType, TrumpSelectionItem>()

    init {
        setItems()
    }

    private fun setItems() {
        removeAllViews()

        addView(TrumpSelectionItem(context).apply {
            setItem(Trump(context.getString(R.string.block_trump_type_clubs), TrumpType.CLUB)) {
                setTrumpSelected(it)
            }
        }.also {
            items[TrumpType.CLUB] = it
        })
        addView(TrumpSelectionItem(context).apply {
            setItem(Trump(context.getString(R.string.block_trump_type_spades), TrumpType.SPADE)) {
                setTrumpSelected(it)
            }
        }.also {
            items[TrumpType.SPADE] = it
        })
        addView(TrumpSelectionItem(context).apply {
            setItem(Trump(context.getString(R.string.block_trump_type_hearts), TrumpType.HEART)) {
                setTrumpSelected(it)
            }
        }.also {
            items[TrumpType.HEART] = it
        })
        addView(TrumpSelectionItem(context).apply {
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
    }

    fun getSelectedTrumpType(): TrumpType {
        val item = items.values.firstOrNull { it.isChecked()}
        return items.filterValues { it == item }.keys.firstOrNull() ?: TrumpType.NONE
    }

    fun setSelectedItem(selectedTrumpType: TrumpType) {
        items[selectedTrumpType]?.setChecked(true)
    }

}