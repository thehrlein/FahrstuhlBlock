package com.tobiapplications.fahrstuhlblock.ui_common.base.activity

import android.content.Intent
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.utils.handler.NavigationHandler
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.extension.dispatchOnActivityResult
import com.tobiapplications.fahrstuhlblock.ui_common.utils.ResourceHelperImpl
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


abstract class BaseActivity<Model: BaseViewModel> : AppCompatActivity() {

    protected abstract val viewModel: Model
    @get:LayoutRes
    protected abstract val layoutId: Int
    protected abstract val viewModelResId: Int
    @get:IdRes
    protected abstract val navHostFragment: Int?

    private val navigationHandler: NavigationHandler by inject {
        parametersOf(this, navHostFragment?.let { findNavController(it) })
    }

    private val navigationObserver = Observer<Screen> {
        navigationHandler.navigateTo(it)
    }

    protected lateinit var binding: ViewDataBinding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.apply {
            lifecycleOwner = provideLifecycleOwner()
            setVariable(viewModelResId, viewModel)
            onBindingCreated(savedInstanceState)
        }
    }

    @CallSuper
    open fun onBindingCreated(savedInstanceState: Bundle?) {
        viewModel.navigationEvent.observe(this, navigationObserver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        supportFragmentManager.dispatchOnActivityResult(requestCode, resultCode, data)
    }

    fun provideLifecycleOwner(): LifecycleOwner = this
}