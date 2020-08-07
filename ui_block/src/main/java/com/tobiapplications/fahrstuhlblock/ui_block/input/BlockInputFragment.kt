package com.tobiapplications.fahrstuhlblock.ui_block.input

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.presentation.block.input.BlockInputViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
import com.tobiapplications.fahrstuhlblock.ui_block.BR
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.FragmentBlockInputBinding
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
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
                        InputType.TIPP -> R.string.block_input_toolbar_title_tipps
                        InputType.RESULT -> R.string.block_input_toolbar_title_results
                        else -> error("could not determine input type")
                    }
                )
            )
        })

        initAdapter()
    }

    private fun initAdapter() {
        BlockInputAdapter(viewModel).also { blockInputAdapter ->
            binding.inputList.apply {
                adapter = blockInputAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
            }

            viewModel.inputModelsItem.observe(viewLifecycleOwner, Observer {
                blockInputAdapter.submitList(it)

                Handler().postDelayed({
                    binding.blockInputSwitcher.displayedChild = BLOCK_INPUT_INDEX
                }, BLOCK_INPUT_DELAY)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_input, menu)
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