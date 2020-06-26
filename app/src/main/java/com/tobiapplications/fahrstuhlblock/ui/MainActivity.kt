package com.tobiapplications.fahrstuhlblock.ui

import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.tobiapplications.fahrstuhlblock.BR
import com.tobiapplications.fahrstuhlblock.R
import com.tobiapplications.fahrstuhlblock.presentation.main.MainViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.activity.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val LOADING_DELAY: Long = 500

class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModel: MainViewModel by viewModel()
    override val layoutId: Int = R.layout.activity_main
    override val viewModelResId: Int = BR.viewModel
    override val navHostFragment: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        showFullscreen()
        super.onCreate(savedInstanceState)

        Handler().postDelayed({

            // start main activity
            viewModel.openNavigation()

            // animate the loading of new activity
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

            // close this activity
            finish()
        }, LOADING_DELAY)
    }

    private fun showFullscreen() {
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // show fullscreen
        window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // hide toolbar
        supportActionBar?.hide()
    }
}
