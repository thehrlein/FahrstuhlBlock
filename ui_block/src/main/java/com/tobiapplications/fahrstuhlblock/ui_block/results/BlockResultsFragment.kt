package com.tobiapplications.fahrstuhlblock.ui_block.results

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockName
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.BlockPlaceholder
import com.tobiapplications.fahrstuhlblock.presentation.block.BlockViewModel
import com.tobiapplications.fahrstuhlblock.presentation.block.results.BlockResultsViewModel
import com.tobiapplications.fahrstuhlblock.ui_block.BR
import com.tobiapplications.fahrstuhlblock.ui_block.R
import com.tobiapplications.fahrstuhlblock.ui_block.databinding.FragmentBlockResultsBinding
import com.tobiapplications.fahrstuhlblock.ui_block.widgets.BlockNameView
import com.tobiapplications.fahrstuhlblock.ui_block.widgets.BlockPlaceHolderView
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.entity.DialogEntity
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogInteractor
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogRequestCode
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogResultCode
import com.tobiapplications.fahrstuhlblock.ui_common.base.fragment.BaseToolbarFragment
import com.tobiapplications.fahrstuhlblock.ui_common.utils.ItemDecoration
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BlockResultsFragment :
    BaseToolbarFragment<BlockResultsViewModel, BlockViewModel, FragmentBlockResultsBinding>(),
    DialogInteractor {

    override val activityToolbarViewModel: BlockViewModel by sharedViewModel()
    override val viewModel: BlockResultsViewModel by viewModel()
    override val layoutId: Int = R.layout.fragment_block_results
    override val viewModelResId: Int = BR.viewModel

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

        activityToolbarViewModel.setTitle(getString(R.string.block_results_toolbar_title))

        activityToolbarViewModel.gameId.observe(viewLifecycleOwner, {
            viewModel.setGameId(it)
        })

        viewModel.editInputEnabled.observe(viewLifecycleOwner, {
            requireActivity().invalidateOptionsMenu()
        })

        viewModel.finishEarlyEnabled.observe(viewLifecycleOwner, {
            requireActivity().invalidateOptionsMenu()
        })

        viewModel.showGameFinishedEvent.observe(viewLifecycleOwner, {
            binding.konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square, Shape.Circle)
                .addSizes(Size(12, 5f))
                .setPosition(-50f, binding.konfettiView.width + 50f, -50f, -50f)
                .streamFor(300, 5000L)

            requireActivity().invalidateOptionsMenu()
        })

        initAdapter()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.showExitDialog()
        }
    }

    private fun initAdapter() {
        BlockResultsAdapter().also { blockResultAdapter ->
            binding.gameList.apply {
                adapter = blockResultAdapter
                addItemDecoration(
                    ItemDecoration(
                        LinearLayout.VERTICAL,
                        context.getDrawable(R.drawable.shape_divider)
                    )
                )
                addItemDecoration(
                    ItemDecoration(
                        LinearLayout.HORIZONTAL,
                        context.getDrawable(R.drawable.shape_divider)
                    )
                )
            }
            viewModel.blockItems.observe(viewLifecycleOwner, { blockItems ->
                val columnCount = viewModel.columnCount.value ?: 0
                val headerItems = blockItems.filter { it is BlockPlaceholder || it is BlockName }
                val gameItems = blockItems.toMutableList().apply {
                    removeAll(headerItems)
                }

                binding.gamePlayerListLayout.apply {
                    removeAllViews()
                    headerItems.forEach {
                        if (it is BlockPlaceholder) {
                            addView(BlockPlaceHolderView(context).apply {
                                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                                setPlaceHolder(it, viewModel)
                            })
                        } else if (it is BlockName) {
                            addView(BlockNameView(context).apply {
                                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)
                                setName(it)
                            })
                        }
                    }
                }

                binding.gameList.layoutManager =
                    GridLayoutManager(requireContext(), columnCount).apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return when (blockResultAdapter.getItemViewType(position)) {
                                    R.layout.item_block_round -> 1
                                    R.layout.item_block_result -> 2
                                    else -> 0
                                }
                            }
                        }
                    }

                blockResultAdapter.submitList(gameItems)
                binding.gameList.scrollToPosition(blockItems.size - 1)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_block, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_delete_input).isEnabled = viewModel.editInputEnabled.value == true
        menu.findItem(R.id.action_finish_early).isEnabled = viewModel.finishEarlyEnabled.value == true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_trophy -> {
                viewModel.onTrophyClicked()
                true
            }
            R.id.action_delete_input -> {
                viewModel.onDeleteInputClicked()
                true
            }
            R.id.action_finish_early -> {
                viewModel.onFinishEarlyClicked()
                true
            }
            R.id.action_info -> {
                viewModel.onInfoClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            DialogRequestCode.CHOOSE_TRUMP -> {
                when (resultCode) {
                    DialogResultCode.POSITIVE -> {
                        (data?.getSerializableExtra(DialogEntity.KEY_DIALOG_ENTITY) as? DialogEntity.Custom.Trump)?.let {
                            viewModel.updateTrumpType(it.selectedTrumpType)
                        }
                    }
                }
            }
            DialogRequestCode.FINISH_EARLY -> {
                when (resultCode) {
                    DialogResultCode.POSITIVE -> viewModel.onFinishEarlyConfirmed()
                }
            }
        }
    }
}
