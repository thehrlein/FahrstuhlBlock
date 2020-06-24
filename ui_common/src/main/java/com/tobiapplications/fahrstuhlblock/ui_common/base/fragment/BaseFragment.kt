package com.tobiapplications.fahrstuhlblock.ui_common.base.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tobiapplications.fahrstuhlblock.entities.utils.handler.NavigationHandler
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.extension.dispatchOnActivityResult
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


abstract class BaseFragment<Model: BaseViewModel, Binding: ViewDataBinding> : Fragment() {

    protected abstract val viewModel: Model
    protected abstract val layoutId: Int
    @get:LayoutRes
    protected abstract val viewModelResId: Int

    private val navigationHandler: NavigationHandler by inject {
        parametersOf(requireActivity(), findNavController())
    }

    protected lateinit var binding: Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<Binding>(inflater, layoutId, container, false).also {
            it.lifecycleOwner = this
            it.setVariable(viewModelResId, viewModel)
            binding = it
            onBindingCreated(savedInstanceState)
        }.root
    }

    /**
     * This is an optional method that will be called to have the fragment instantiate
     * its binding variables or other view related configurations.
     * It will be called in [onCreateView] before the [View] will be returned.
     *
     * It is recommended to move logic that operates on the returned View to [onViewCreated].
     */
    @CallSuper
    open fun onBindingCreated(savedInstanceState: Bundle?) {
        viewModel.navigationEvent.observe(viewLifecycleOwner, Observer {
            navigationHandler.navigateTo(it)
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        childFragmentManager.dispatchOnActivityResult(requestCode, resultCode, data)
    }
}