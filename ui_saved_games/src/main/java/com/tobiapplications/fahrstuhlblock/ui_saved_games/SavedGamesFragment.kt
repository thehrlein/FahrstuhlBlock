package com.tobiapplications.fahrstuhlblock.ui_saved_games

import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.tobiapplications.fahrstuhlblock.presentation.savedgames.SavedGamesViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseFragment
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
            }
            viewModel.savedGames.observe(viewLifecycleOwner, Observer {
                savedGamesAdapter.submitList(it)
            })
        }
    }
}
