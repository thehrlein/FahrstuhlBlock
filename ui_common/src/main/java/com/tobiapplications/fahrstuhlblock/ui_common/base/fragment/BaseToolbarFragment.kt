package com.tobiapplications.fahrstuhlblock.ui_common.base.fragment

import androidx.databinding.ViewDataBinding
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseToolbarViewModel
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel


abstract class BaseToolbarFragment<Model : BaseViewModel, ToolbarViewModel: BaseToolbarViewModel, Binding : ViewDataBinding> :
    BaseFragment<Model, Binding>() {

    abstract val activityToolbarViewModel : ToolbarViewModel
}