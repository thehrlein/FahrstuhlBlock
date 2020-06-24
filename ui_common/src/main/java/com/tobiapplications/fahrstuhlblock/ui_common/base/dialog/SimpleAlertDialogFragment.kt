package com.tobiapplications.fahrstuhlblock.ui_common.base.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.entity.DialogData
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogResultCode

class SimpleAlertDialogFragment : BaseInteractionDialogFragment() {

    companion object {
        private val TAG: String = SimpleAlertDialogFragment::class.java.simpleName

        fun show(fragmentManager: FragmentManager, dialogData: DialogData.Text) {
            if (fragmentManager.findFragmentByTag(TAG) != null) return

            val bundle = Bundle().apply {
                putSerializable(DialogData.KEY_DIALOG_DATA, dialogData)
            }

            SimpleAlertDialogFragment().apply {
                arguments = bundle
            }.showNow(fragmentManager, TAG)
        }
    }

    private val dialogData: DialogData.Text by lazy {
        requireArguments().getSerializable(DialogData.KEY_DIALOG_DATA) as DialogData.Text
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setCancelable(false)
            .setTitle(dialogData.title)
            .setMessage(dialogData.message)
            .setNeutralButton(dialogData.neutralButtonText) { _, _ ->
                sendResult(dialogData, DialogResultCode.NEUTRAL)
            }
            .setNegativeButton(dialogData.negativeButtonText) { _, _ ->
                sendResult(dialogData, DialogResultCode.NEGATIVE)
            }
            .setPositiveButton(dialogData.positiveButtonText) { _, _ ->
                sendResult(dialogData, DialogResultCode.POSITIVE)
            }
            .create().also {
                isCancelable = dialogData.isCancelable
            }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        if (dialogData.negativeButtonText != null) {
            sendResult(dialogData, DialogResultCode.NEGATIVE)
        } else {
            sendResult(dialogData, DialogResultCode.POSITIVE)
        }
    }
}