package com.tobiapplications.fahrstuhlblock.ui_block.results

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockResultsViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
import com.tobiapplications.fahrstuhlblock.ui_block.BR
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.FragmentBlockResultsBinding
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
import com.tobiapplications.fahrstuhlblock.ui_common.extension.executeAfter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BlockResultsFragment :
    BaseToolbarFragment<BlockResultsViewModel, BlockViewModel, FragmentBlockResultsBinding>() {

    override val activityToolbarViewModel: BlockViewModel by sharedViewModel()
    override val viewModel: BlockResultsViewModel by viewModel()
    override val layoutId: Int = R.layout.fragment_block_results
    override val viewModelResId: Int = BR.viewModel

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        binding.executeAfter {
            this.blockViewModel = activityToolbarViewModel
        }
        activityToolbarViewModel.setTitle(getString(R.string.block_results_toolbar_title))

        activityToolbarViewModel.gameId.observe(viewLifecycleOwner, Observer {
            viewModel.setGameId(it)
        })

        viewModel.openInputEvent.observe(viewLifecycleOwner, Observer {
            activityToolbarViewModel.openInput()
        })

        initAdapter()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activityToolbarViewModel.showExitDialog()
        }
    }

    private fun initAdapter() {
        BlockResultAdapter().also { blockResultAdapter ->
            binding.gameList.apply {
                adapter = blockResultAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            }

            viewModel.names.observe(viewLifecycleOwner, Observer {
                blockResultAdapter.submitList(listOf(it))
            })
        }

    }


}