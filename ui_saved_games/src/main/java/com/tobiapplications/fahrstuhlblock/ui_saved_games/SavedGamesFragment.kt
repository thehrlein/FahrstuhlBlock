package com.tobiapplications.fahrstuhlblock.ui_saved_games

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tobiapplications.fahrstuhlblock.presentation.savedgames.SavedGamesViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseFragment
import com.tobiapplications.fahrstuhlblock.ui_common.utils.SwipeToDeleteCallback
import com.tobiapplications.fahrstuhlblock.ui_saved_games.databinding.FragmentSavedGamesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SavedGamesFragment : BaseFragment<SavedGamesViewModel, FragmentSavedGamesBinding>() {

    override val viewModel: SavedGamesViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.fragment_saved_games
    override val viewModelResId: Int = BR.viewModel

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        SavedGamesAdapter(viewModel).also { savedGamesAdapter ->
            binding.gameList.apply {
                adapter = savedGamesAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))

                val itemTouchHelper = ItemTouchHelper(object : SwipeToDeleteCallback(context) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        if (viewHolder is SavedGamesAdapter.SavedGameViewHolder) {
                            viewModel.onGameRemoved(viewHolder.binding.item)
                        }
                    }
                })

                itemTouchHelper.attachToRecyclerView(this)
            }

            viewModel.savedGames.observe(viewLifecycleOwner, Observer {
                savedGamesAdapter.submitList(it)
            })
        }
    }
}
