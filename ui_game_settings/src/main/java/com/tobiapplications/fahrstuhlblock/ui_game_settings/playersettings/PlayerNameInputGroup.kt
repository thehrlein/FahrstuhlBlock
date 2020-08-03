package com.tobiapplications.fahrstuhlblock.ui_game_settings.playersettings

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerError
import com.tobiapplications.fahrstuhlblock.ui_common.extension.layoutInflater
import com.tobiapplications.fahrstuhlblock.ui_game_settings.R
import com.tobiapplications.fahrstuhlblock.ui_game_settings.databinding.WidgetPlayerNameInputGroupBinding

class PlayerNameInputGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val inputViews = mutableListOf<PlayerNameInputView>()
    private val inputStates = mutableMapOf<Int, Pair<Boolean, String?>>()

    private val binding: WidgetPlayerNameInputGroupBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_player_name_input_group,
        this,
        true
    )

    init {
        inputViews.add(binding.playerOneLayout)
        inputViews.add(binding.playerTwoLayout)
        inputViews.add(binding.playerThreeLayout)
        inputViews.add(binding.playerFourLayout)
        inputViews.add(binding.playerFiveLayout)
        inputViews.add(binding.playerSixLayout)
        inputViews.add(binding.playerSevenLayout)
        inputViews.add(binding.playerEightLayout)

        inputViews.forEach {
            it.setPlayerError(null)
        }

        inputStates[1] = Pair(false, null)
        inputStates[2] = Pair(false, null)
        inputStates[3] = Pair(false, null)
        inputStates[4] = Pair(false, null)
        inputStates[5] = Pair(false, null)
        inputStates[6] = Pair(false, null)
        inputStates[7] = Pair(false, null)
        inputStates[8] = Pair(false, null)

        setTextChangeListener()
    }

    private fun setTextChangeListener() {
        setTextChangeListenerFor(binding.playerOneLayout, 1)
        setTextChangeListenerFor(binding.playerTwoLayout, 2)
        setTextChangeListenerFor(binding.playerThreeLayout, 3)
        setTextChangeListenerFor(binding.playerFourLayout, 4)
        setTextChangeListenerFor(binding.playerFiveLayout, 5)
        setTextChangeListenerFor(binding.playerSixLayout, 6)
        setTextChangeListenerFor(binding.playerSevenLayout, 7)
        setTextChangeListenerFor(binding.playerEightLayout, 8)

    }

    private fun setTextChangeListenerFor(inputView: PlayerNameInputView, position: Int) {
        inputView.addTextChangeListener {
            inputStates[position] = Pair(true, it)
            if (it.isNotEmpty()) {
                inputView.setError(null)
            }
        }
    }

    fun setPlayerNames(playerNames: List<String>?) {
        if (playerNames == null) return
        playerNames.forEachIndexed { index, name ->
            inputViews[index].setPlayerName(name)
        }
    }

    fun setPlayerCount(count: Int) {
        inputViews.forEachIndexed { index, inputView ->
            val visible = index < count
            inputView.setVisible(visible)
            inputStates[index + 1] = Pair(visible, inputStates[index + 1]?.second)
        }
    }

    fun setPlayerNameOptions(playerNames: Set<String>?) {
        playerNames?.let { names ->
            inputViews.forEach {
                it.setAdapter(names)
            }
        }
    }

    fun getValues(): List<Pair<Int, String?>>? {
        val visibleInputs = inputStates.filter { it.value.first }
        var isError = false
        visibleInputs.forEach { input ->
            isError = checkIfValidOrError(input, visibleInputs, isError)
        }

        return if (isError) {
            null
        } else {
            inputStates.filter { it.value.first }.map {
                it.key to it.value.second
            }
        }
    }

    private fun checkIfValidOrError(
        input: Map.Entry<Int, Pair<Boolean, String?>>,
        visibleInputs: Map<Int, Pair<Boolean, String?>>,
        isError: Boolean
    ): Boolean {
        var isLocalError = isError
        val text = input.value.second
        val error = when {
            text.isNullOrEmpty() -> PlayerError.EMPTY
            visibleInputs.filter { it.key != input.key }.map { it.value.second }
                .contains(text) -> PlayerError.DUPLICATE
            else -> null
        }

        if (error != null) {
            isLocalError = true
        }
        inputViews[input.key - 1].setPlayerError(error)

        return isLocalError
    }
}