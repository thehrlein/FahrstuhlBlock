package com.tobiapplications.fahrstuhlblock.ui_common.base.dialog

import android.content.Context
import android.content.Intent
import androidx.fragment.app.DialogFragment
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.entity.DialogData
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogInteractor

abstract class BaseInteractionDialogFragment : DialogFragment() {

    private var interactor: DialogInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        interactor = context as DialogInteractor
    }

    override fun onDetach() {
        super.onDetach()
        interactor = null
    }

    protected fun sendResult(dialogData: DialogData, resultCode: Int, data: Intent? = null) {
        val intentData = data ?: Intent()
        intentData.putExtra(DialogData.KEY_DIALOG_DATA, dialogData)
        interactor?.onDialogResult(dialogData.requestCode, resultCode, intentData)
    }
}