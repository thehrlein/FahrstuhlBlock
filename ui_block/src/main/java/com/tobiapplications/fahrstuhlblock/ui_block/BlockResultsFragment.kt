package com.tobiapplications.fahrstuhlblock.ui_block

import android.os.Bundle
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockResultsViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
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

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(getString(R.string.block_results_toolbar_title))
    }
}