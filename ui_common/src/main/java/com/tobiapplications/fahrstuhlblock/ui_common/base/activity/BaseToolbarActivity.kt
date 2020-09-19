package com.tobiapplications.fahrstuhlblock.ui_common.base.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.tobiapplications.fahrstuhlblock.entities.general.toolbar.ToolbarButtonType
import com.tobiapplications.fahrstuhlblock.presentation.general.BaseToolbarViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.BR
import com.tobiapplications.fahrstuhlblock.ui_common.R
import com.tobiapplications.fahrstuhlblock.ui_common.databinding.ActivityBaseToolbarBinding
import com.tobiapplications.fahrstuhlblock.ui_common.views.BaseToolbar

abstract class BaseToolbarActivity<Model : BaseToolbarViewModel, Binding : ViewDataBinding> :
    BaseActivity<Model>() {

    final override val layoutId: Int = R.layout.activity_base_toolbar
    final override val viewModelResId: Int = BR.toolbarViewModel
    protected abstract var toolbarButtonType: ToolbarButtonType
    protected abstract val toolbarTitle: String?
    protected abstract val contentViewModelResId: Int
    @get:LayoutRes
    protected abstract val contentLayoutRes: Int

    protected lateinit var contentBinding: ViewDataBinding
        private set

    open fun onToolbarButtonClicked() {
        when (toolbarButtonType) {
            ToolbarButtonType.Close -> finish()
            ToolbarButtonType.Back -> onBackPressed()
            else -> return
        }
    }

    final override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        val activityBaseToolbarBinding = binding as ActivityBaseToolbarBinding
        setUpToolbar(activityBaseToolbarBinding.baseToolbar)
        setupToolbarViewModel()
        createContentBinding(activityBaseToolbarBinding.baseContentContainer, savedInstanceState)
    }

    private fun createContentBinding(
        container: ViewGroup,
        savedInstanceState: Bundle?
    ) {
        DataBindingUtil.inflate<Binding>(layoutInflater, contentLayoutRes, container, true).also {
            contentBinding = it
            contentBinding.lifecycleOwner = provideLifecycleOwner()
            contentBinding.setVariable(contentViewModelResId, viewModel)
            onContentBindingCreated(savedInstanceState)
        }
    }

    @CallSuper
    open fun onContentBindingCreated(savedInstanceState: Bundle?) = Unit

    private fun setUpToolbar(toolbar: BaseToolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun setupToolbarViewModel() {
        viewModel.apply {
            setToolbarButton(toolbarButtonType)
            this@BaseToolbarActivity.toolbarTitle?.let {
                setTitle(it)
            }

            toolbarEvent.observe(this@BaseToolbarActivity, Observer {
                onToolbarButtonClicked()
            })
            toolbarButton.observe(this@BaseToolbarActivity, Observer<ToolbarButtonType> {
                toolbarButtonType = it
            })
        }
    }
}
