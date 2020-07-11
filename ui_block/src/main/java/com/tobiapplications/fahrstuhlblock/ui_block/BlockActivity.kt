package com.tobiapplications.fahrstuhlblock.ui_block

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.tobiapplications.fahrstuhlblock.entities.general.toolbar.ToolbarButtonType
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.ActivityBlockBinding
import com.tobiapplications.fahrstuhlblock.ui_common.base.activity.BaseToolbarActivity
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogInteractor
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogRequestCode
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogResultCode
import com.tobiapplications.fahrstuhlblock.ui_common.extension.dispatchOnDialogResult
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val DEFAULT_GAME_ID: Long = 1

class BlockActivity : BaseToolbarActivity<BlockViewModel, ActivityBlockBinding>(),
    DialogInteractor {

    override var toolbarButtonType: ToolbarButtonType = ToolbarButtonType.Back
    override val toolbarTitle: String? = null
    override val contentViewModelResId: Int = BR.viewModel
    override val contentLayoutRes: Int = R.layout.activity_block
    override val viewModel: BlockViewModel by viewModel {
        parametersOf(gameId)
    }
    override val navHostFragment: Int? = R.id.activity_block_nav_host_fragment

    private val gameId: Long
        get() = intent?.getLongExtra(BLOCK_INTENT_KEY, DEFAULT_GAME_ID) ?: DEFAULT_GAME_ID

    companion object {
        private const val BLOCK_INTENT_KEY = "key.block_intent"

        fun start(activity: AppCompatActivity, gameId: Long) {
            val intent = Intent(activity, BlockActivity::class.java)
                .putExtra(BLOCK_INTENT_KEY, gameId)
            activity.startActivity(intent)
            activity.finishAffinity()
        }
    }

    override fun onToolbarButtonClicked() {
        onBackPressed()
    }

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            DialogRequestCode.BLOCK_EXIT -> {
                when (resultCode) {
                    DialogResultCode.POSITIVE -> viewModel.openMenu()
                    DialogResultCode.NEGATIVE -> finish()
                    DialogResultCode.NEUTRAL -> Unit
                }
            }
            else -> supportFragmentManager.dispatchOnDialogResult(requestCode, resultCode, data)
        }
    }
}