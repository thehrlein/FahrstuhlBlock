package com.tobiapplications.fahrstuhlblock.entities.general

import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.GameScore
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.GameScoreData
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import com.tobiapplications.fahrstuhlblock.entities.models.settings.GameRuleSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData


sealed class Screen {

    sealed class Main : Screen() {
        object Menu : Main()
    }

    sealed class Menu : Screen() {
        object NewGame : Menu()
        object SavedGames: Menu()
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
        class Scores(val gameScoreData: GameScoreData) : Block()
        class GameFinished(val winners: List<GameScore>) : Block()
        class Trump(val selectedTrumpType: TrumpType) : Block()
        object About : Block()
    }

    sealed class Input : Screen() {
        object Block : Input()
        class Info(val inputType: InputType, val cardCount: Int, val round: Int) : Input()
    }

    sealed class SavedGames : Screen() {
        class ContinueGame(val gameId: Long) : SavedGames()
    }
}