package com.tobiapplications.fahrstuhlblock.ui_common.base.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tobiapplications.fahrstuhlblock.ui_common.R
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.entity.DialogEntity

class FullscreenProgressDialogFragment : DialogInteractionFragment() {

    companion object {
        private val TAG: String = FullscreenProgressDialogFragment::class.java.simpleName

        fun show(fragmentManager: FragmentManager, dialogEntity: DialogEntity.Progress) {
            if (fragmentManager.findFragmentByTag(TAG) != null) return

            val bundle = Bundle().apply {
                putSerializable(DialogEntity.KEY_DIALOG_ENTITY, dialogEntity)
            }

            FullscreenProgressDialogFragment().apply {
                isCancelable = dialogEntity.isCancelable
                arguments = bundle
            }.showNow(fragmentManager, TAG)
        }

        fun hide(fragmentManager: FragmentManager) {
            val fragment = fragmentManager.findFragmentByTag(TAG) as DialogFragment?
            fragment?.dismiss()
        }
    }

    private val dialogEntity: DialogEntity.Progress by lazy {
        requireArguments().getSerializable(DialogEntity.KEY_DIALOG_ENTITY) as DialogEntity.Progress
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
                // sets view
            .setView(R.layout.dialog_progress)
                // set background to transparent
            .setBackground(ColorDrawable(Color.TRANSPARENT))
            .create().also {
                // dim window if requested
                dimWindow(it)
            }
    }

    private fun dimWindow(dialog: AlertDialog) {
        if (!dialogEntity.dimWindow) {
            val windowAttributes = dialog.window?.attributes?.apply {
                dimAmount = 0.0f
            }
            dialog.window?.attributes = windowAttributes
        }
    }
}