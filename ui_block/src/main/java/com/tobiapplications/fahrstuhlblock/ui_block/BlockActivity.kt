package com.tobiapplications.fahrstuhlblock.ui_block

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.tobiapplications.fahrstuhlblock.entities.general.toolbar.ToolbarButtonType
import com.tobiapplications.fahrstuhlblock.entities.models.game.FahrstuhlGame
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.ActivityBlockBinding
import com.tobiapplications.fahrstuhlblock.ui_common.base.activity.BaseToolbarActivity
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogInteractor
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogRequestCode
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogResultCode
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val BLOCK_INTENT_KEY = "key.block_game"

class BlockActivity : BaseToolbarActivity<BlockViewModel, ActivityBlockBinding>(), DialogInteractor {

    override var toolbarButtonType: ToolbarButtonType = ToolbarButtonType.Back
    override val toolbarTitle: String? = null
    override val contentViewModelResId: Int = BR.viewModel
    override val contentLayoutRes: Int = R.layout.activity_block
    override val viewModel: BlockViewModel by viewModel {
        parametersOf(fahrstuhlGame)
    }
    override val navHostFragment: Int? = null

    private val fahrstuhlGame: FahrstuhlGame
        get() = intent?.getSerializableExtra(BLOCK_INTENT_KEY) as FahrstuhlGame

    companion object {
        fun start(activity: AppCompatActivity, fahrstuhlGame: FahrstuhlGame) {
            val intent = Intent(activity, BlockActivity::class.java)
                .putExtra(BLOCK_INTENT_KEY, fahrstuhlGame)
            activity.startActivity(intent)
            activity.finishAffinity()
        }
    }

    override fun onToolbarButtonClicked() {
        viewModel.showExitDialog()
    }

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            DialogRequestCode.BLOCK_EXIT -> {
                when (resultCode) {
                    DialogResultCode.POSITIVE -> viewModel.openMenu()
                    DialogResultCode.NEGATIVE -> finish()
                }
            }
        }
    }
}