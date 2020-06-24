package com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tobiapplications.fahrstuhlblock.ui_common.R
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.BaseInteractionDialogFragment
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.entity.DialogData

class ProgressDialogFragment : BaseInteractionDialogFragment() {

    companion object {
        private val TAG: String = ProgressDialogFragment::class.java.simpleName

        fun show(fragmentManager: FragmentManager, dialogData: DialogData.Progress) {
            if (fragmentManager.findFragmentByTag(TAG) != null) return
            val bundle = Bundle().apply {
                putSerializable(DialogData.KEY_DIALOG_DATA, dialogData)
            }

            ProgressDialogFragment().apply {
                isCancelable = false
                arguments = bundle
            }.showNow(fragmentManager, TAG)
        }

        fun hide(fragmentManager: FragmentManager) {
            val fragment = fragmentManager.findFragmentByTag(TAG) as DialogFragment?
            fragment?.dismiss()
        }
    }

    private val dialogData: DialogData.Progress by lazy {
        requireArguments().getSerializable(DialogData.KEY_DIALOG_DATA) as DialogData.Progress
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setView(R.layout.dialog_progress)
            .setBackground(ColorDrawable(Color.TRANSPARENT))
            .create().also {
                adjustWindowDim(it)
            }
    }

    private fun adjustWindowDim(dialog: AlertDialog) {
        if (!dialogData.dimWindow) {
            val window = dialog.window
            val windowAttributes = window?.attributes?.apply {
                dimAmount = 0.0f
            }
            window?.attributes = windowAttributes
        }
    }
}