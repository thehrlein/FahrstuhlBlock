package com.tobiapplications.fahrstuhlblock.ui_game_settings.gamerules

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
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

        activityToolbarViewModel.setTitle(getString(R.string.game_rules_toolbar_title))

        initMaxCardSelection()
        initAutoShowTrumpDialog()
        initStopAtMaxCardCountCheckbox()
    }

    private fun initAutoShowTrumpDialog() {
        binding.autoShowTrumpDialog.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAutoShowTrumpDialogChanged(isChecked)
        }
    }

    private fun initStopAtMaxCardCountCheckbox() {
        binding.gameRulesCardCountRadioGroupInclude.stopAtMaxCardCount.setOnCheckedChangeListener { _, isChecked ->
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
        menu.findItem(R.id.action_info).icon.setTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.color_on_primary
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