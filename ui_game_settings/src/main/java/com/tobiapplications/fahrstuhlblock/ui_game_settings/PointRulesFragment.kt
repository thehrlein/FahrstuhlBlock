package com.tobiapplications.fahrstuhlblock.ui_game_settings

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.tobiapplications.fahrstuhlblock.presentation.settings.GameSettingsViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.PointRulesViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
import com.tobiapplications.fahrstuhlblock.ui_game_settings.databinding.FragmentPointRulesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PointRulesFragment : BaseToolbarFragment<PointRulesViewModel, GameSettingsViewModel, FragmentPointRulesBinding>() {

    override val activityToolbarViewModel: GameSettingsViewModel by sharedViewModel()
    override val viewModel: PointRulesViewModel by viewModel {
        parametersOf(args.gameRuleSettingsData)
    }
    override val layoutId: Int = R.layout.fragment_point_rules
    override val viewModelResId: Int = BR.viewModel
    private val args: PointRulesFragmentArgs by navArgs()

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(getString(R.string.point_rules_toolbar_title))

    }
}