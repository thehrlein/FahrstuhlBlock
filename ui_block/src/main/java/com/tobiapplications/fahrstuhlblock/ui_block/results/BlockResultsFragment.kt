package com.tobiapplications.fahrstuhlblock.ui_block.results

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.tobiapplications.fahrstuhlblock.presentation.block.results.BlockResultsViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
import com.tobiapplications.fahrstuhlblock.ui_block.BR
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.FragmentBlockResultsBinding
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BlockResultsFragment :
    BaseToolbarFragment<BlockResultsViewModel, BlockViewModel, FragmentBlockResultsBinding>() {

    override val activityToolbarViewModel: BlockViewModel by sharedViewModel()
    override val viewModel: BlockResultsViewModel by viewModel()
    override val layoutId: Int = R.layout.fragment_block_results
    override val viewModelResId: Int = BR.viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(getString(R.string.block_results_toolbar_title))

        activityToolbarViewModel.gameId.observe(viewLifecycleOwner, Observer {
            viewModel.setGameId(it)
        })

        viewModel.openInputEvent.observe(viewLifecycleOwner, Observer {
            activityToolbarViewModel.openInput()
        })

        viewModel.openExitDialogEvent.observe(viewLifecycleOwner, Observer {
            activityToolbarViewModel.showExitDialog()
        })

        viewModel.gameScores.observe(viewLifecycleOwner, Observer {

        })

        initAdapter()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activityToolbarViewModel.showExitDialog()
        }
    }

    private fun initAdapter() {
        BlockResultsAdapter().also { blockResultAdapter ->
            binding.gameList.apply {
                adapter = blockResultAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
                addItemDecoration(DividerItemDecoration(context, LinearLayout.HORIZONTAL))
            }
            viewModel.blockItems.observe(viewLifecycleOwner, Observer { blockItems ->
                blockResultAdapter.submitList(blockItems)
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_block, menu)
//        menu.findItem(R.id.trophy).icon.setTint(
//            ContextCompat.getColor(
//                requireContext(),
//                R.color.mb_white
//            )
//        )
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.trophy -> {
                viewModel.onTrophyClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}