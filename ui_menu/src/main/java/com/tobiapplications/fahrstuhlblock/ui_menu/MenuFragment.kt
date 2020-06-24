package com.tobiapplications.fahrstuhlblock.ui_menu

import com.tobiapplications.fahrstuhlblock.presentation.menu.MenuViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseFragment
import com.tobiapplications.fahrstuhlblock.ui_menu.databinding.FragmentMenuBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MenuFragment : BaseFragment<MenuViewModel, FragmentMenuBinding>() {

    override val viewModel: MenuViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.fragment_menu
    override val viewModelResId: Int = BR.viewModel
}
