package com.tobiapplications.fahrstuhlblock.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHostController
import com.tobiapplications.fahrstuhlblock.entities.general.Screen
import com.tobiapplications.fahrstuhlblock.entities.utils.extensions.checkAllMatched
import com.tobiapplications.fahrstuhlblock.entities.utils.handler.NavigationHandler
import com.tobiapplications.fahrstuhlblock.ui_block.BlockActivity
import com.tobiapplications.fahrstuhlblock.ui_block.input.BlockInputFragmentDirections
import com.tobiapplications.fahrstuhlblock.ui_block.results.BlockResultsFragmentDirections
import com.tobiapplications.fahrstuhlblock.ui_block.trump.BlockTrumpDialog
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.SimpleAlertDialogFragment
import com.tobiapplications.fahrstuhlblock.ui_common.base.dialog.entity.DialogEntity
import com.tobiapplications.fahrstuhlblock.ui_common.utils.ResourceHelper
import com.tobiapplications.fahrstuhlblock.ui_game_settings.GameSettingsActivity
import com.tobiapplications.fahrstuhlblock.ui_game_settings.gamerules.GameRulesFragmentDirections
import com.tobiapplications.fahrstuhlblock.ui_game_settings.playerorder.PlayerOrderFragmentDirections
import com.tobiapplications.fahrstuhlblock.ui_game_settings.playersettings.PlayerSettingsFragmentDirections
import com.tobiapplications.fahrstuhlblock.ui_menu.MenuActivity
import com.tobiapplications.fahrstuhlblock.ui_saved_games.SavedGamesActivity


class NavigationHandlerImpl(
    private val activity: AppCompatActivity,
    private val navHostController: NavHostController?,
    private val resourceHelper: ResourceHelper
) : NavigationHandler {

    override fun navigateTo(screen: Screen) {
        when (screen) {
            is Screen.Main -> navigateTo(screen)
            is Screen.Menu -> navigateTo(screen)
            is Screen.PlayerSettings -> navigateTo(screen)
            is Screen.PlayerOrder -> navigateTo(screen)
            is Screen.GameRules -> navigateTo(screen)
            is Screen.PointRules -> navigateTo(screen)
            is Screen.Block -> navigateTo(screen)
            is Screen.Input -> navigateTo(screen)
            is Screen.SavedGames -> navigateTo(screen)
        }.checkAllMatched
    }

    // navigate from main
    private fun navigateTo(screen: Screen.Main) {
        when (screen) {
            is Screen.Main.Menu -> MenuActivity.start(activity)
        }.checkAllMatched
    }

    // navigate from menu
    private fun navigateTo(screen: Screen.Menu) {
        when (screen) {
            is Screen.Menu.NewGame -> GameSettingsActivity.start(activity)
            is Screen.Menu.SavedGames -> SavedGamesActivity.start(activity)
            is Screen.Menu.NewGame2 -> BlockActivity.start(
                activity,
                1
            )
        }.checkAllMatched
    }

    // navigate from player settings
    private fun navigateTo(screen: Screen.PlayerSettings) {
        when (screen) {
            is Screen.PlayerSettings.PlayerOrder -> {
                val action =
                    PlayerSettingsFragmentDirections.actionPlayerSettingsFragmentToPlayerOrderFragment(
                        screen.playerSettingsData
                    )
                navHostController?.navigate(action)
            }
        }.checkAllMatched
    }

    // navigate from player order
    private fun navigateTo(screen: Screen.PlayerOrder) {
        when (screen) {
            is Screen.PlayerOrder.GameRules -> {
                val action =
                    PlayerOrderFragmentDirections.actionPlayerOrderFragmentToGameRulesFragment(
                        screen.playerSettingsData
                    )
                navHostController?.navigate(action)
            }
        }.checkAllMatched
    }

    // navigate from game rules
    private fun navigateTo(screen: Screen.GameRules) {
        when (screen) {
            is Screen.GameRules.PointRules -> {
                val action =
                    GameRulesFragmentDirections.actionGameRulesFragmentToPointRulesFragment(screen.gameRuleSettingsData)
                navHostController?.navigate(action)
            }
        }.checkAllMatched
    }

    // navigate from point rules
    private fun navigateTo(screen: Screen.PointRules) {
        when (screen) {
            is Screen.PointRules.Block -> BlockActivity.start(activity, screen.gameId)
        }.checkAllMatched
    }

    // navigate from block
    private fun navigateTo(screen: Screen.Block) {
        when (screen) {
            is Screen.Block.Exit -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.Exit(resourceHelper)
            )
            is Screen.Block.Menu -> MenuActivity.start(activity)
            is Screen.Block.Input -> {
                val action =
                    BlockResultsFragmentDirections.actionBlockResultsFragmentToBlockInputFragment(
                        screen.gameId
                    )
                navHostController?.navigate(action)
            }
            is Screen.Block.Scores -> {
                val action =
                    BlockResultsFragmentDirections.actionBlockResultsFragmentToBlockScoresFragment(
                        screen.gameScoreData
                    )
                navHostController?.navigate(action)
            }
            is Screen.Block.GameFinished -> SimpleAlertDialogFragment.show(
                activity.supportFragmentManager,
                DialogEntity.Text.GameFinished(screen.winners, resourceHelper)
            )
            is Screen.Block.Trump -> BlockTrumpDialog.show(
                activity.supportFragmentManager,
                DialogEntity.Custom.Trump(resourceHelper, screen.selectedTrumpType)
            )
        }.checkAllMatched
    }

    // navigate from tipp / result input
    private fun navigateTo(screen: Screen.Input) {
        when (screen) {
            is Screen.Input.Block -> navHostController?.navigate(BlockInputFragmentDirections.actionBlockInputFragmentToBlockResultsFragment())
        }.checkAllMatched
    }

    // navigate from saved games
    private fun navigateTo(screen: Screen.SavedGames) {
        when (screen) {
            is Screen.SavedGames.ContinueGame -> BlockActivity.start(
                activity,
                screen.gameId
            )
        }.checkAllMatched
    }
}