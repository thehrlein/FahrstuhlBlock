package com.tobiapplications.fahrstuhlblock.feature.menu

import com.tobiapplications.fahrstuhlblock.core.presentation.menu.MenuViewModel
import com.tobiapplications.fahrstuhlblock.feature.common.base.fragment.BaseFragment
import com.tobiapplications.fahrstuhlblock.feature.menu.databinding.FragmentMenuBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MenuFragment : BaseFragment<MenuViewModel, FragmentMenuBinding>() {

    override val viewModel: MenuViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.fragment_menu
    override val viewModelResId: Int = BR.viewModel
}
