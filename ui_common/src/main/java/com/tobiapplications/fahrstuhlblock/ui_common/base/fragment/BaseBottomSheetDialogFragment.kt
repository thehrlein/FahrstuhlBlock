package com.tobiapplications.fahrstuhlblock.ui_common.base.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.entity.DialogData
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogInteractor
import com.tobiapplications.fahrstuhlblock.ui_common.extension.dispatchOnActivityResult

abstract class BaseBottomSheetDialogFragment<Model : BaseViewModel, Binding : ViewDataBinding> :
    BottomSheetDialogFragment() {

    private var interactor: DialogInteractor? = null
    protected abstract val viewModel: Model
    protected abstract val viewModelVariableId: Int
    @get:LayoutRes
    protected abstract val layoutId: Int
    protected lateinit var binding: Binding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        interactor = context as DialogInteractor
    }

    override fun onDetach() {
        super.onDetach()
        interactor = null
    }

    protected fun sendResult(dialogData: DialogData, resultCode: Int, data: Intent? = null) {
        interactor?.onDialogResult(dialogData.requestCode, resultCode, data)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<Binding>(inflater, layoutId, container, false).also {
            binding = it
            binding.lifecycleOwner = this
            binding.setVariable(viewModelVariableId, viewModel)
            onBindingCreated()
        }.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        childFragmentManager.dispatchOnActivityResult(requestCode, resultCode, data)
    }

    /**
     * This is an optional method that will be called to have the DialogFragment instantiate
     * its binding variables or other view related configurations.
     * It will be called in [onCreateDialog] before the [Dialog] will be returned.
     *
     * It is recommended to move logic that operates on the returned View to [onViewCreated].
     */
    @CallSuper
    open fun onBindingCreated() {

    }

}