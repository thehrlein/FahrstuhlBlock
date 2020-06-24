package com.tobiapplications.fahrstuhlblock.ui_common.base.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tobiapplications.fahrstuhlblock.entities.utils.handler.NavigationHandler
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.utils.ResourceHelperImpl
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


/**
 * Created by tobiashehrlein on 28.04.20.
 */
abstract class BaseDialogFragment<Model: BaseViewModel, Binding: ViewDataBinding> : BaseInteractionDialogFragment() {

    protected abstract val viewModel: Model
    protected abstract val layoutId: Int
    protected abstract val viewModelVariableId: Int
    protected lateinit var binding: Binding

    private val navigationHandler: NavigationHandler by inject {
        parametersOf(requireActivity(), findNavController(), ResourceHelperImpl(requireContext()))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        DataBindingUtil.inflate<Binding>(
            LayoutInflater.from(context), layoutId, null, false
        ).also {
            binding = it
            binding.lifecycleOwner = this
            binding.setVariable(viewModelVariableId, viewModel)
            onBindingCreated()
        }

        return createDialog(savedInstanceState, binding.root)
    }

    abstract fun createDialog(savedInstanceState: Bundle?, view: View): AlertDialog

    /**
     * This is an optional method that will be called to have the DialogFragment instantiate
     * its binding variables or other view related configurations.
     * It will be called in [onCreateDialog] before the [Dialog] will be returned.
     *
     * It is recommended to move logic that operates on the returned View to [onViewCreated].
     */
    @CallSuper
    open fun onBindingCreated() {
        viewModel.navigationEvent.observe(this, Observer {
            navigationHandler.navigateTo(it)
        })
    }
}