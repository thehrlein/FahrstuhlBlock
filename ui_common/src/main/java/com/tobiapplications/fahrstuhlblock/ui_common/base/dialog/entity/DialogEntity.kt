package com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.entity

import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.GameScore
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import com.tobiapplications.fahrstuhlblock.ui_common.R
import java.io.Serializable
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.utils.DialogRequestCode
import com.tobiapplications.fahrstuhlblock.ui_common.utils.ResourceHelper

sealed class DialogEntity : Serializable {

    companion object {
        const val KEY_DIALOG_ENTITY = "key.dialog_entity"
    }

    abstract val requestCode: Int
    open val isCancelable: Boolean = false

    sealed class Text(
        val title: String? = null,
        val message: String? = null,
        val positiveButtonText: String? = null,
        val negativeButtonText: String? = null,
        val neutralButtonText: String? = null
    ) : DialogEntity() {

        class Exit(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(R.string.dialog_title_attention),
            message = resourceHelper.getString(R.string.dialog_exit_message),
            positiveButtonText = resourceHelper.getString(R.string.dialog_exit_button_menu),
            negativeButtonText = resourceHelper.getString(R.string.dialog_exit_button_quit),
            neutralButtonText = resourceHelper.getString(R.string.general_cancel)
        ) {
            override val requestCode: Int = DialogRequestCode.BLOCK_EXIT
        }

        class GameFinished(winners: List<GameScore>, resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(R.string.block_results_winner_title),
            message = resourceHelper.getPlural(R.plurals.game_winner_message, winners.size, winners.joinToString { it.player }, winners.first().points),
            positiveButtonText = resourceHelper.getString(R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.GAME_FINISHED
        }

        class PlayerOrderInfo(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(R.string.dialog_title_info),
            message = resourceHelper.getString(R.string.player_order_description),
            neutralButtonText = resourceHelper.getString(R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.INPUT_INFO
        }

        class GameRulesInfo(stopAtHighCard: Boolean, resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(R.string.dialog_title_info),
            message = resourceHelper.getString(if (stopAtHighCard) R.string.game_rules_card_count_description_stop_at_highcard else R.string.game_rules_card_count_description_dont_stop_at_highcard),
            neutralButtonText = resourceHelper.getString(R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.INPUT_INFO
        }

        class PointRuleInfo(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(R.string.dialog_title_info),
            message = resourceHelper.getString(R.string.point_rules_description),
            neutralButtonText = resourceHelper.getString(R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.INPUT_INFO
        }

        class InputInfo(inputType: InputType, cardCount: Int, round: Int, resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(R.string.dialog_title_info),
            message = when (inputType) {
                InputType.TIPP -> if (round == 1) {
                    resourceHelper.getString(R.string.block_input_tipps_message_first_round)
                } else {
                    resourceHelper.getString(R.string.block_input_tipps_message, cardCount)
                }
                InputType.RESULT -> resourceHelper.getString(R.string.block_input_result_message, cardCount)
            },
            neutralButtonText = resourceHelper.getString(R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.INPUT_INFO
        }

        class FinishEarly(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(R.string.dialog_title_attention),
            message = resourceHelper.getString(R.string.block_results_finish_early_message),
            positiveButtonText = resourceHelper.getString(R.string.dialog_finish_game_button),
            negativeButtonText = resourceHelper.getString(R.string.general_cancel)
        ) {
            override val requestCode: Int = DialogRequestCode.FINISH_EARLY
        }
    }

    sealed class Custom(
        val title: String?,
        val positiveButtonText: String? = null,
        val negativeButtonText: String? = null,
        val neutralButtonText: String? = null
    ) : DialogEntity() {

        class Trump(var selectedTrumpType: TrumpType, resourceHelper: ResourceHelper) : Custom(
            title = resourceHelper.getString(R.string.block_trump_title),
            positiveButtonText = resourceHelper.getString(R.string.general_ok),
            negativeButtonText = resourceHelper.getString(R.string.general_cancel)
        ) {
            override val requestCode: Int = DialogRequestCode.CHOOSE_TRUMP
            override val isCancelable: Boolean
                get() = true
        }

    }

    class Progress(
        val dimWindow: Boolean
    ) : DialogEntity() {
        override val requestCode: Int = DialogRequestCode.DIALOG_PROGRESS
        override val isCancelable: Boolean = false
    }

}