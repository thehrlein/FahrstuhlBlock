package com.tobiapplications.fahrstuhlblock.feature.common.base.dialog.utils

import android.content.Intent

interface DialogInteractor {

    fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?)
}
