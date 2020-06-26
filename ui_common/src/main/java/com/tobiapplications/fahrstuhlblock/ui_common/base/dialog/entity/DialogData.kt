package com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.entity

import com.tobiapplications.fahrstuhlblock.ui_common.R
import java.io.Serializable
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogRequestCode
import com.tobiapplications.fahrstuhlblock.ui_common.utils.ResourceHelper

sealed class DialogData : Serializable {

    companion object {
        const val KEY_DIALOG_DATA = "key.dialog_data"
    }

    abstract val requestCode: Int
    open val isCancelable: Boolean = false

    sealed class Text(
        val title: String? = null,
        val message: String? = null,
        val positiveButtonText: String? = null,
        val negativeButtonText: String? = null,
        val neutralButtonText: String? = null
    ) : DialogData() {

        class Exit(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(R.string.dialog_exit_title),
            message = resourceHelper.getString(R.string.dialog_exit_message),
            positiveButtonText = resourceHelper.getString(R.string.dialog_exit_button_menu),
            negativeButtonText = resourceHelper.getString(R.string.dialog_exit_button_quit)
        ) {
            override val requestCode: Int = DialogRequestCode.BLOCK_EXIT
        }
    }

    sealed class TypeCustom(
        val positiveButtonText: String? = null,
        val negativeButtonText: String? = null,
        val neutralButtonText: String? = null
    ) : DialogData() {

    }

    class Progress(
        val dimWindow: Boolean
    ) : DialogData() {
        override val requestCode: Int = DialogRequestCode.DIALOG_PROGRESS
    }

}