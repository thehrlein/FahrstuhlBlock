package com.tobiapplications.fahrstuhlblock.ui_common.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import com.tobiapplications.fahrstuhlblock.ui_common.R

@BindingAdapter("trump")
fun ImageView.setTrump(trumpType: TrumpType?) {
    trumpType?.let {
        when (trumpType) {
            TrumpType.CLUB -> setImageResource(R.drawable.ic_card_club)
            TrumpType.SPADE -> setImageResource(R.drawable.ic_card_spade)
            TrumpType.HEART -> setImageResource(R.drawable.ic_card_heart)
            TrumpType.DIAMOND -> setImageResource(R.drawable.ic_card_diamond)
            else -> Unit
        }
    }
}