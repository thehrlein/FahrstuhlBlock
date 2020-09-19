package com.tobiapplications.fahrstuhlblock.ui_game_settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.tobiapplications.fahrstuhlblock.entities.general.toolbar.ToolbarButtonType
import com.tobiapplications.fahrstuhlblock.presentation.settings.GameSettingsViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.activity.BaseToolbarActivity
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogInteractor
import com.tobiapplications.fahrstuhlblock.ui_common.extension.dispatchOnDialogResult
import com.tobiapplications.fahrstuhlblock.ui_game_settings.databinding.ActivityGameSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameSettingsActivity : BaseToolbarActivity<GameSettingsViewModel, ActivityGameSettingsBinding>(), DialogInteractor {

    override var toolbarButtonType: ToolbarButtonType = ToolbarButtonType.Back
    override val toolbarTitle: String? = null
    override val viewModel: GameSettingsViewModel by viewModel()
    override val contentViewModelResId: Int = BR.viewModel
    override val contentLayoutRes: Int = R.layout.activity_game_settings
    override val navHostFragment: Int? = R.id.activity_game_nav_host_fragment

    companion object {
        fun start(activity: AppCompatActivity) {
            val intent = Intent(activity, GameSettingsActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        supportFragmentManager.dispatchOnDialogResult(requestCode, resultCode, data)
    }
}
