package com.tobiapplications.fahrstuhlblock.ui_game_settings

import android.os.Bundle
import com.tobiapplications.fahrstuhlblock.presentation.settings.GameSettingsViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.PlayerSettingsViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
import com.tobiapplications.fahrstuhlblock.ui_game_settings.databinding.FragmentPlayerSettingsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerSettingsFragment : BaseToolbarFragment<PlayerSettingsViewModel, GameSettingsViewModel, FragmentPlayerSettingsBinding>() {

    override val viewModel: PlayerSettingsViewModel by viewModel()
    override val activityToolbarViewModel: GameSettingsViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.fragment_player_settings
    override val viewModelResId: Int = BR.viewModel

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(getString(R.string.game_player_settings_toolbar_title))

        binding.playerCountChooseButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                viewModel.setPlayerCount(when (checkedId) {
                    R.id.toggle_four -> 4
                    R.id.toggle_five -> 5
                    R.id.toggle_six -> 6
                    R.id.toggle_seven -> 7
                    R.id.toggle_eight -> 8
                    else -> 3
                })
            }
        }
    }

}