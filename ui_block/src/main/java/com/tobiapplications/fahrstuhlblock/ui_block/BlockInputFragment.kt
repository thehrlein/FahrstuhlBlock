package com.tobiapplications.fahrstuhlblock.ui_block

import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockInputViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.FragmentBlockInputBinding
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseFragment
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BlockInputFragment : BaseToolbarFragment<BlockInputViewModel, BlockViewModel, FragmentBlockInputBinding>() {

    override val viewModel: BlockInputViewModel by viewModel()
    override val activityToolbarViewModel: BlockViewModel by sharedViewModel()
    override val viewModelResId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_block_input

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle("XO")

//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//
//        }
    }
}