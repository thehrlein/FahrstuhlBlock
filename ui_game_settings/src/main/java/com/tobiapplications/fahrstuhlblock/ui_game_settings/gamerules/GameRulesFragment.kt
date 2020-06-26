package com.tobiapplications.fahrstuhlblock.ui_game_settings.gamerules

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.tobiapplications.fahrstuhlblock.entities.models.settings.MaxCardCountSelection
import com.tobiapplications.fahrstuhlblock.presentation.settings.gamerules.GameRulesViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.GameSettingsViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
import com.tobiapplications.fahrstuhlblock.ui_game_settings.BR
import com.tobiapplications.fahrstuhlblock.ui_game_settings.R
import com.tobiapplications.fahrstuhlblock.ui_game_settings.databinding.FragmentGameRulesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class GameRulesFragment :
    BaseToolbarFragment<GameRulesViewModel, GameSettingsViewModel, FragmentGameRulesBinding>() {

    override val viewModel: GameRulesViewModel by viewModel {
        parametersOf(args.playerSettingsData)
    }
    override val activityToolbarViewModel: GameSettingsViewModel by sharedViewModel()
    override val layoutId: Int =
        R.layout.fragment_game_rules
    override val viewModelResId: Int =
        BR.viewModel
    private val args: GameRulesFragmentArgs by navArgs()


    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(getString(R.string.game_rules_toolbar_title))

        initMaxCardSelection()
    }

    private fun initMaxCardSelection() {
        binding.gameRulesCardCountRadioGroupInclude.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.setSelectedCardOption(
                when (checkedId) {
                    R.id.options_one_deck -> MaxCardCountSelection.ONE_DECK
                    R.id.options_two_decks -> MaxCardCountSelection.TWO_DECKS
                    R.id.options_individual -> MaxCardCountSelection.INDIVIDUAL
                    else -> error("selection of radio group not possible")
                }
            )
        }
    }
}