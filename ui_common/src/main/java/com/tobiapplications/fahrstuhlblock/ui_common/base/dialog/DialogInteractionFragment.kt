package com.tobiapplications.fahrstuhlblock.ui_common.base.dialog

import android.content.Context
import android.content.Intent
import androidx.fragment.app.DialogFragment
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.entity.DialogEntity
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogInteractor

abstract class DialogInteractionFragment : DialogFragment() {

    private var dialogInteractor: DialogInteractor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dialogInteractor = context as DialogInteractor
    }

    override fun onDetach() {
        super.onDetach()
        dialogInteractor = null
    }

    protected fun sendDialogResult(dialogEntity: DialogEntity, resultCode: Int, data: Intent? = null) {
        val intentData = data ?: Intent()
        intentData.putExtra(DialogEntity.KEY_DIALOG_ENTITY, dialogEntity)
        dialogInteractor?.onDialogResult(dialogEntity.requestCode, resultCode, intentData)
    }
}