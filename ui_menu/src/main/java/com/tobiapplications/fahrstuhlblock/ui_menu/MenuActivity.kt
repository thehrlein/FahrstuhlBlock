package com.tobiapplications.fahrstuhlblock.ui_menu

import android.app.Activity
import android.content.Intent
import com.tobiapplications.fahrstuhlblock.entities.general.toolbar.ToolbarButtonType
import com.tobiapplications.fahrstuhlblock.presentation.menu.MenuViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.activity.BaseToolbarActivity
import com.tobiapplications.fahrstuhlblock.ui_menu.databinding.ActivityMenuBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuActivity :
    BaseToolbarActivity<MenuViewModel, ActivityMenuBinding>() {

    override var toolbarButtonType: ToolbarButtonType = ToolbarButtonType.None
    override val toolbarTitle: String?
        get() = getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.app_name)
    override val viewModel: MenuViewModel by viewModel()
    override val contentViewModelResId: Int = BR.viewModel
    override val contentLayoutRes: Int = R.layout.activity_menu
    override val navHostFragment: Int? = R.id.activity_navigation_main_nav_host_fragment

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, MenuActivity::class.java)
            activity.startActivity(intent)
            activity.finishAffinity()
        }
    }
}
