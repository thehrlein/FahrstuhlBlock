package com.tobiapplications.fahrstuhlblock.ui_block

import com.tobiapplications.fahrstuhlblock.entities.models.game.general.Game
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.GameInfo
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PlayerSettingsData
import com.tobiapplications.fahrstuhlblock.entities.models.settings.PointsRuleData
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class GameTest {

    @Test
    fun `new game without rounds`() {
        val game = Game(
            gameInfo = GameInfo(
                gameId = 1,
                gameStartDate = 12345,
                highCardCount = 5,
                players = PlayerSettingsData(listOf("a", "b", "c")),
                pointsRuleData = PointsRuleData(
                    correctPoints = 10,
                    pointsPerStitch = 3,
                    minusPointsPerStitch = 3,
                    pointsIfPredictionFalse = false
                ),
                gameFinished = false
            ),
            rounds = emptyList()
        )

        assertThat(game.maxRound, `is`(10))
        assertThat(game.previousTotals, `is`(listOf(0,0,0)))
        assertThat(game.lastPlayedRound, `is`(nullValue()))
        assertThat(game.lastRoundNumber, `is`(nullValue()))
        assertThat(game.inputType, `is`(InputType.TIPP))
        assertThat(game.currentCardCount, `is`(1))
        assertThat(game.currentRoundNumber, `is`(1))
        assertThat(game.currentRound?.round, `is`(1))
        assertThat(game.currentRound?.cardCount, `is`(1))
    }
}