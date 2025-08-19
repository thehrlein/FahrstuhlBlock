package com.tobiapplications.fahrstuhlblock.feature.common.extension

import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.tobiapplications.fahrstuhlblock.feature.common.base.dialog.utils.DialogInteractor

fun FragmentManager.dispatchOnDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
    fragments.forEach loop@{
        when {
            !it.isVisible -> return@loop
            it is NavHostFragment -> it.childFragmentManager.dispatchOnDialogResult(
                requestCode,
                resultCode,
                data
            )
            it is DialogInteractor -> it.onDialogResult(requestCode, resultCode, data)
        }
    }
}

fun FragmentManager.dispatchOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    fragments.forEach {
        if (it.isVisible) {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }
}
