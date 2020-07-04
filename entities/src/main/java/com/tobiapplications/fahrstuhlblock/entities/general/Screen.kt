package com.tobiapplications.fahrstuhlblock.entities.general

import com.tobiapplications.fahrstuhlblock.entities.models.settings.GameRuleSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData

/**
 *
 * This class will be used to declare all application screens and
 * there optional shared arguments such as IDs.
 * It will be used as an abstract component to navigate throw the application.
 *
 * The interpretation of all [Screen] classes is part of the framework layer.
 */

sealed class Screen {

    sealed class Main : Screen() {
        object Menu : Main()
    }

    sealed class Menu : Screen() {
        object NewGame : Menu()
        object NewGame2 : Menu()
    }

    sealed class PlayerSettings : Screen() {
        class PlayerOrder(val playerSettingsData: PlayerSettingsData) : PlayerSettings()
    }

    sealed class PlayerOrder : Screen() {
        class GameRules(val playerSettingsData: PlayerSettingsData) : PlayerOrder()
    }

    sealed class GameRules : Screen() {
        class PointRules(val gameRuleSettingsData: GameRuleSettingsData) : GameRules()
    }

    sealed class PointRules : Screen() {
        class Block(val gameId: Long) : PointRules()
    }

    sealed class Block : Screen() {
        object Exit : Block()
        object Menu : Block()
        class Input(val gameId: Long) : Block()
    }

    sealed class Input : Screen() {
        object Block : Input()
    }

}