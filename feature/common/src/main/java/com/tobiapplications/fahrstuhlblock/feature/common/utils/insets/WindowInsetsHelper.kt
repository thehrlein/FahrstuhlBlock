package com.tobiapplications.fahrstuhlblock.feature.common.utils.insets

import android.content.res.Resources
import android.os.Build
import android.view.View
import android.view.Window
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.tobiapplications.fahrstuhlblock.core.entities.general.OHInsets

object WindowInsetsHelper {

    fun getInsets(view: View, onHeightDetermined: (OHInsets) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 and above, use WindowInsets to get the status bar height
            val setOnApplyWindowInsetsListener =
                OnApplyWindowInsetsListener { v: View?, insets: WindowInsetsCompat? ->
                    // Get the system bars insets
                    val topBar = insets?.getInsets(
                        WindowInsetsCompat.Type.systemBars()
                                or WindowInsetsCompat.Type.displayCutout()
                    )
                    val navigationBar = insets?.getInsets(
                        WindowInsetsCompat.Type.navigationBars()
                    )
                    val ohInsets = OHInsets(
                        statusBarHeight = topBar?.top ?: 0,
                        navigationBarHeight = navigationBar?.bottom ?: 0
                    )

                    onHeightDetermined(ohInsets)
                    ViewCompat.setOnApplyWindowInsetsListener(view, null)
                    WindowInsetsCompat.CONSUMED
                }
            ViewCompat.setOnApplyWindowInsetsListener(view, setOnApplyWindowInsetsListener)
        } else {
            val resources: Resources = view.context.resources
            val statusBarResourceId =
                resources.getIdentifier("status_bar_height", "dimen", "android")
            val statusBarHeight = if (statusBarResourceId > 0) {
                resources.getDimensionPixelSize(statusBarResourceId)
            } else {
                0
            }
            // Use the resource identifier for status bar height
            val resourceId: Int =
                resources.getIdentifier("navigation_bar_height", "dimen", "android")
            val navigationBarHeight = if (resourceId > 0) {
                resources.getDimensionPixelSize(resourceId)
            } else {
                0
            }

            val ohInsets = OHInsets(
                statusBarHeight = statusBarHeight,
                navigationBarHeight = navigationBarHeight
            )
            onHeightDetermined(ohInsets)
        }
    }

    // works on API 23+
    fun tintStatusBarIconsLight(light: Boolean, window: Window) {
        val windowInsetController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetController.isAppearanceLightStatusBars = !light
    }
}