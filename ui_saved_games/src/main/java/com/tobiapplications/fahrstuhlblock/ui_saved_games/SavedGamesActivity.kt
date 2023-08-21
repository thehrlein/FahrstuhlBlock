package com.tobiapplications.fahrstuhlblock.ui_saved_games

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.tobiapplications.fahrstuhlblock.entities.general.toolbar.ToolbarButtonType
import com.tobiapplications.fahrstuhlblock.presentation.savedgames.SavedGamesViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.activity.BaseToolbarActivity
import com.tobiapplications.fahrstuhlblock.ui_saved_games.databinding.ActivitySavedGamesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedGamesActivity : BaseToolbarActivity<SavedGamesViewModel, ActivitySavedGamesBinding>() {

    override var toolbarButtonType: ToolbarButtonType = ToolbarButtonType.Back
    override val toolbarTitle: String?
        get() = getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.saved_game_toolbar_title)
    override val contentViewModelResId: Int = BR.viewModel
    override val contentLayoutRes: Int = R.layout.activity_saved_games
    override val viewModel: SavedGamesViewModel by viewModel()
    override val navHostFragment: Int? = R.id.activity_saved_games_nav_host_fragment

    companion object {
        fun start(activity: AppCompatActivity) {
            val intent = Intent(activity, SavedGamesActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
