package com.tobiapplications.fahrstuhlblock.ui_block.input

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.input.BlockInputViewModel
import com.tobiapplications.fahrstuhlblock.ui_block.BR
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.FragmentBlockInputBinding
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
import com.tobiapplications.fahrstuhlblock.ui_common.extension.getColorReference
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val BLOCK_INPUT_INDEX = 1
private const val BLOCK_INPUT_DELAY: Long = 300

class BlockInputFragment :
    BaseToolbarFragment<BlockInputViewModel, BlockViewModel, FragmentBlockInputBinding>() {

    override val viewModel: BlockInputViewModel by viewModel {
        parametersOf(navArgs.gameId)
    }
    override val activityToolbarViewModel: BlockViewModel by sharedViewModel()
    override val viewModelResId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_block_input
    private val navArgs: BlockInputFragmentArgs by navArgs()

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

        viewModel.inputType.observe(viewLifecycleOwner, Observer {
            activityToolbarViewModel.setTitle(
                getString(
                    when (it) {
                        InputType.TIPP -> com.tobiapplications.fahrstuhlblock.ui_common.R.string.block_input_toolbar_title_tipps
                        InputType.RESULT -> com.tobiapplications.fahrstuhlblock.ui_common.R.string.block_input_toolbar_title_results
                        else -> error("could not determine input type")
                    }
                )
            )
        })

        viewModel.trumpType.observe(viewLifecycleOwner) {
            requireActivity().invalidateOptionsMenu()
        }

        initAdapter()
    }

    private fun initAdapter() {
        BlockInputAdapter(viewModel).also { blockInputAdapter ->
            binding.inputList.apply {
                adapter = blockInputAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            }

            viewModel.inputModelsItem.observe(viewLifecycleOwner) {
                blockInputAdapter.submitList(it)

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.blockInputSwitcher.displayedChild = BLOCK_INPUT_INDEX
                }, BLOCK_INPUT_DELAY)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_input, menu)
        menu.findItem(R.id.action_info).icon?.setTint(
            ContextCompat.getColor(
                requireContext(),
                com.tobiapplications.fahrstuhlblock.ui_common.R.color.color_on_primary
            )
        )
        val toolbarIcon = setToolbarTrumpIcon(viewModel.trumpType.value)
        menu.findItem(R.id.action_trump).apply {
            icon = toolbarIcon?.first
            isVisible = toolbarIcon != null
            title = toolbarIcon?.second
        }

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

    private fun setToolbarTrumpIcon(trumpType: TrumpType?): Pair<Drawable?, String>? {
        return context?.let {
            when (trumpType) {
                TrumpType.CLUB -> {
                    Pair(it.getDrawable(com.tobiapplications.fahrstuhlblock.ui_common.R.drawable.ic_card_club)?.apply {
                        setTintList(ColorStateList.valueOf(it.getColorReference(com.google.android.material.R.attr.colorOnPrimary)))
                    }, it.getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.block_trump_type_clubs))
                }
                TrumpType.SPADE -> {
                    Pair(it.getDrawable(com.tobiapplications.fahrstuhlblock.ui_common.R.drawable.ic_card_spade)?.apply {
                        setTintList(ColorStateList.valueOf(it.getColorReference(com.google.android.material.R.attr.colorOnPrimary)))
                    }, it.getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.block_trump_type_spades))
                }
                TrumpType.HEART -> {
                    Pair(it.getDrawable(com.tobiapplications.fahrstuhlblock.ui_common.R.drawable.ic_card_heart)?.apply {
                        setTintList(ColorStateList.valueOf(ContextCompat.getColor(it, com.tobiapplications.fahrstuhlblock.ui_common.R.color.color_card_red)))
                    }, it.getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.block_trump_type_hearts))
                }
                TrumpType.DIAMOND -> {
                    Pair(it.getDrawable(com.tobiapplications.fahrstuhlblock.ui_common.R.drawable.ic_card_diamond)?.apply {
                        setTintList(ColorStateList.valueOf(ContextCompat.getColor(it, com.tobiapplications.fahrstuhlblock.ui_common.R.color.color_card_red)))
                    }, it.getString(com.tobiapplications.fahrstuhlblock.ui_common.R.string.block_trump_type_diamonds))
                }
                else -> null
            }
        }
    }
}
