package com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils

import android.content.Intent

interface DialogInteractor {

    fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?)
}
