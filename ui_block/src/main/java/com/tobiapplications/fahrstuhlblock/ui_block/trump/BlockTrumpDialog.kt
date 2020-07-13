package com.tobiapplications.fahrstuhlblock.ui_block.trump

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tobiapplications.fahrstuhlblock.presentation.block.trump.BlockTrumpViewModel
import com.tobiapplications.fahrstuhlblock.ui_block.BR
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.DialogBlockTrumpBinding
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.BaseDialogFragment
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.entity.DialogEntity
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogResultCode
import org.koin.androidx.viewmodel.ext.android.viewModel

class BlockTrumpDialog : BaseDialogFragment<BlockTrumpViewModel, DialogBlockTrumpBinding>() {

    override val viewModel: BlockTrumpViewModel by viewModel()
    override val layoutId: Int = R.layout.dialog_block_trump
    override val viewModelVariableId: Int = BR.viewModel

    companion object {
        private val TAG: String = BlockTrumpDialog::class.java.simpleName

        fun show(fragmentManager: FragmentManager, dialogEntity: DialogEntity.Custom) {
            if (fragmentManager.findFragmentByTag(TAG) != null) return
            val bundle = Bundle().apply {
                putSerializable(DialogEntity.KEY_DIALOG_ENTITY, dialogEntity)
            }

            BlockTrumpDialog().apply {
                arguments = bundle
                isCancelable = false
            }.show(fragmentManager, TAG)
        }

        fun dismiss(fragmentManager: FragmentManager) {
            val dialog = fragmentManager.findFragmentByTag(TAG) as? DialogFragment
            dialog?.dismiss()
        }
    }

    private val dialogEntity: DialogEntity.Custom.Trump by lazy {
        requireArguments().getSerializable(DialogEntity.KEY_DIALOG_ENTITY) as DialogEntity.Custom.Trump
    }

    override fun createDialog(savedInstanceState: Bundle?, view: View): AlertDialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .setTitle(dialogEntity.title)
            .setPositiveButton(dialogEntity.positiveButtonText) { _, _ ->
                dialogEntity.selectedTrumpType = binding.trumpSelectionGroup.getSelectedTrumpType()
                sendDialogResult(dialogEntity, DialogResultCode.POSITIVE)
            }
            .setNegativeButton(dialogEntity.negativeButtonText) { _, _ ->
                sendDialogResult(dialogEntity, DialogResultCode.NEGATIVE)
            }
            .setNeutralButton(dialogEntity.neutralButtonText) { _, _ ->
                sendDialogResult(dialogEntity, DialogResultCode.NEUTRAL)
            }
            .create()
    }

    override fun onBindingCreated() {
        super.onBindingCreated()

        binding.trumpSelectionGroup.setSelectedItem(dialogEntity.selectedTrumpType)
        binding.autoShowTrumpDialog.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAutoShowTrumpDialogChanged(isChecked)
        }
    }
}