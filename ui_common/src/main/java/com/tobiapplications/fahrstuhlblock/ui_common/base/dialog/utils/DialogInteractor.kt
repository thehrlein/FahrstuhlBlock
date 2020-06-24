package com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils

import android.content.Intent

interface DialogInteractor {

    /**
     * Dispatch the dialog result to the associated activity.
     *
     * @param requestCode The integer request code originally supplied to show() the dialog, allowing you to identify who this result came from.
     * @param resultCode The integer result code returned by the dialog action.
     * @param data An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?)
}