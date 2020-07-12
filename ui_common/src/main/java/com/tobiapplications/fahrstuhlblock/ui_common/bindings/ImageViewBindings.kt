package com.tobiapplications.fahrstuhlblock.ui_common.bindings

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import com.tobiapplications.fahrstuhlblock.ui_common.R
import com.tobiapplications.fahrstuhlblock.ui_common.extension.getColorReference

@BindingAdapter("trump")
fun ImageView.setTrump(trumpType: TrumpType?) {
    trumpType?.let {
        when (trumpType) {
            TrumpType.CLUB -> {
                setImageResource(R.drawable.ic_card_club)
                imageTintList = ColorStateList.valueOf(context.getColorReference(R.attr.colorOnBackground))
            }
            TrumpType.SPADE -> {
                setImageResource(R.drawable.ic_card_spade)
                imageTintList = ColorStateList.valueOf(context.getColorReference(R.attr.colorOnBackground))
            }
            TrumpType.HEART -> {
                setImageResource(R.drawable.ic_card_heart)
                imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.color_card_red))
            }
            TrumpType.DIAMOND -> {
                setImageResource(R.drawable.ic_card_diamond)
                imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.color_card_red))
            }
            else -> Unit
        }
    }
}