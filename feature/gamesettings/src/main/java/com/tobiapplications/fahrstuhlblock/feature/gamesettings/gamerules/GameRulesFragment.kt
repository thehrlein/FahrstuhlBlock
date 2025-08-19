package com.tobiapplications.fahrstuhlblock.feature.gamesettings.gamerules

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.tobiapplications.fahrstuhlblock.core.entities.models.settings.MaxCardCountSelection
import com.tobiapplications.fahrstuhlblock.core.presentation.settings.gamerules.GameRulesViewModel
import com.tobiapplications.fahrstuhlblock.core.presentation.settings.GameSettingsViewModel
import com.tobiapplications.fahrstuhlblock.feature.common.base.fragment.BaseToolbarFragment
import com.tobiapplications.fahrstuhlblock.feature.gamesettings.BR
import com.tobiapplications.fahrstuhlblock.feature.gamesettings.R
import com.tobiapplications.fahrstuhlblock.feature.gamesettings.databinding.FragmentGameRulesBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GameRulesFragment :
    BaseToolbarFragment<GameRulesViewModel, GameSettingsViewModel, FragmentGameRulesBinding>() {

    override val viewModel: GameRulesViewModel by viewModel {
        parametersOf(args.playerSettingsData)
    }
    override val activityToolbarViewModel: GameSettingsViewModel by activityViewModel()
    override val layoutId: Int =
        R.layout.fragment_game_rules
    override val viewModelResId: Int =
        BR.viewModel
    private val args: GameRulesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(getString(com.tobiapplications.fahrstuhlblock.feature.common.R.string.game_rules_toolbar_title))

        initMaxCardSelection()
        initAutoShowTrumpDialog()
        initStopAtMaxCardCountCheckbox()
        initFirstRoundSettings()
    }

    private fun initFirstRoundSettings() {
        binding.firstRoundTipsCanBeOne.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onFirstRoundTipsCanBeOneChanged(isChecked)
        }
    }

    private fun initAutoShowTrumpDialog() {
        binding.autoShowTrumpDialog.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAutoShowTrumpDialogChanged(isChecked)
        }
    }

    private fun initStopAtMaxCardCountCheckbox() {
        binding.stopAtMaxCardCount.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onStopAtMaxCardCountClicked(isChecked)
        }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_game_rules, menu)
        menu.findItem(R.id.action_info).icon?.setTint(
            ContextCompat.getColor(
                requireContext(),
                com.tobiapplications.fahrstuhlblock.feature.common.R.color.color_on_primary
            )
        )
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                viewModel.onInfoIconClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
