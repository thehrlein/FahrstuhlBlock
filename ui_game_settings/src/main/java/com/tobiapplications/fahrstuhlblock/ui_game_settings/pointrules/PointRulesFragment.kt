package com.tobiapplications.fahrstuhlblock.ui_game_settings.pointrules

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.tobiapplications.fahrstuhlblock.presentation.settings.GameSettingsViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.pointrules.PointRulesViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
import com.tobiapplications.fahrstuhlblock.ui_game_settings.BR
import com.tobiapplications.fahrstuhlblock.ui_game_settings.R
import com.tobiapplications.fahrstuhlblock.ui_game_settings.databinding.FragmentPointRulesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PointRulesFragment :
    BaseToolbarFragment<PointRulesViewModel, GameSettingsViewModel, FragmentPointRulesBinding>() {

    override val activityToolbarViewModel: GameSettingsViewModel by sharedViewModel()
    override val viewModel: PointRulesViewModel by viewModel {
        parametersOf(args.gameRuleSettingsData)
    }
    override val layoutId: Int = R.layout.fragment_point_rules
    override val viewModelResId: Int = BR.viewModel
    private val args: PointRulesFragmentArgs by navArgs()

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

        activityToolbarViewModel.setTitle(getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.point_rules_toolbar_title))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_point_rules, menu)
        menu.findItem(R.id.action_info).icon?.setTint(
            ContextCompat.getColor(
                requireContext(),
                com.tobiapplications.fahrstuhlblock.ui_common.R.color.color_on_primary
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
