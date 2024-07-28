package com.tobiapplications.fahrstuhlblock.ui_game_settings.playerorder

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tobiapplications.fahrstuhlblock.presentation.settings.playerorder.PlayerOrderInteractions
import com.tobiapplications.fahrstuhlblock.ui_common.extension.layoutInflater
import com.tobiapplications.fahrstuhlblock.ui_game_settings.R
import com.tobiapplications.fahrstuhlblock.ui_game_settings.databinding.ItemPlayerRowBinding
import kotlin.collections.ArrayList

class PlayerOrderAdapter(
    private val interactions: PlayerOrderInteractions
) : RecyclerView.Adapter<PlayerOrderAdapter.PlayerOrderViewHolder>(),
    ItemTouchHelperAdapter {

    private val items = mutableListOf<String>()
    private var onItemTouchListener: ((RecyclerView.ViewHolder) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerOrderViewHolder {
        return PlayerOrderViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_player_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayerOrderViewHolder, position: Int) {
        holder.bind(items[position], onItemTouchListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(list: List<String>) {
        items.clear()
        items.addAll(list)
    }

    fun setOnItemTouchListener(onItemTouchListener: ((RecyclerView.ViewHolder) -> Unit)) {
        this.onItemTouchListener = onItemTouchListener
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val prev: String = items.removeAt(fromPosition)
        items.add(toPosition, prev)
        notifyItemMoved(fromPosition, toPosition)
        interactions.onPlayerOrderChanged(ArrayList(items))
    }

    inner class PlayerOrderViewHolder(private val binding: ItemPlayerRowBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(item: String, onItemTouchListener: ((RecyclerView.ViewHolder) -> Unit)?) {
            binding.playerName.text = item
            binding.playerPosition.text =
                binding.root.context.getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.player_order_position, adapterPosition + 1)
            binding.iconDrag.setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    onItemTouchListener?.invoke(this)
                }
                true
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
            notifyDataSetChanged()
        }
    }
}
