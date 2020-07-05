package com.tobiapplications.fahrstuhlblock.ui_block.scores

import android.os.Bundle
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.scores.BlockScoresViewModel
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.FragmentBlockScoresBinding
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.BR

class BlockScoresFragment : BaseToolbarFragment<BlockScoresViewModel, BlockViewModel, FragmentBlockScoresBinding>() {

    override val activityToolbarViewModel: BlockViewModel by sharedViewModel()
    override val viewModel: BlockScoresViewModel by viewModel()
    override val layoutId: Int = R.layout.fragment_block_scores
    override val viewModelResId: Int = BR.viewModel

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(getString(R.string.block_scores_toolbar_title))
    }
}