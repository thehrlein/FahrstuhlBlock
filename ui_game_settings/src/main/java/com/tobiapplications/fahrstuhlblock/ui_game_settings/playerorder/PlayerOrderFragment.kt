package com.tobiapplications.fahrstuhlblock.ui_game_settings.playerorder

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.tobiapplications.fahrstuhlblock.presentation.settings.GameSettingsViewModel
import com.tobiapplications.fahrstuhlblock.presentation.settings.playerorder.PlayerOrderViewModel
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
import com.tobiapplications.fahrstuhlblock.ui_game_settings.BR
import com.tobiapplications.fahrstuhlblock.ui_game_settings.R
import com.tobiapplications.fahrstuhlblock.ui_game_settings.databinding.FragmentPlayerOrderBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlayerOrderFragment :
    BaseToolbarFragment<PlayerOrderViewModel, GameSettingsViewModel, FragmentPlayerOrderBinding>() {

    override val viewModel: PlayerOrderViewModel by viewModel {
        parametersOf(args.playerSettingsData)
    }
    override val activityToolbarViewModel: GameSettingsViewModel by sharedViewModel()
    override val layoutId: Int = R.layout.fragment_player_order
    override val viewModelResId: Int = BR.viewModel
    private val args: PlayerOrderFragmentArgs by navArgs()

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

        activityToolbarViewModel.setTitle(getString(R.string.player_order_toolbar_title))


        PlayerOrderAdapter(viewModel)
            .also { playerOrderAdapter ->
            binding.playerList.apply {
                adapter = playerOrderAdapter

                addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))

                val callback = SimpleItemTouchHelper(playerOrderAdapter)
                val itemTouchHelper = ItemTouchHelper(callback)
                itemTouchHelper.attachToRecyclerView(this)
                playerOrderAdapter.setOnItemTouchListener {
                    itemTouchHelper.startDrag(it)
                }
            }

            viewModel.playerNames.observe(viewLifecycleOwner, Observer {
                playerOrderAdapter.setItems(it)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_player_order, menu)
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