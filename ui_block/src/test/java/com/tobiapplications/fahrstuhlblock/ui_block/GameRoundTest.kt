package com.tobiapplications.fahrstuhlblock.ui_block

import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerResultData
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.PlayerTippData
import com.tobiapplications.fahrstuhlblock.entities.models.game.general.GameRound
import com.tobiapplications.fahrstuhlblock.entities.models.game.input.InputType
import com.tobiapplications.fahrstuhlblock.entities.models.game.result.TrumpType
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class GameRoundTest {

    @Test
    fun `inputTypeForThisRound if tipps and result set is null`() {
        val round = GameRound(
            round = 1,
            cardCount = 1,
            playerTippData = listOf(
                PlayerTippData(tipp = 0),
                PlayerTippData(tipp = 0),
                PlayerTippData(tipp = 0)
            ),
            playerResultData = listOf(
                PlayerResultData(
                    result = 1,
                    difference = -10,
                    total = -10
                ),
                PlayerResultData(
                    result = 0,
                    difference = 30,
                    total = 30
                ),
                PlayerResultData(
                    result = 0,
                    difference = 30,
                    total = 30
                )
            ),
            trumpType = TrumpType.NONE
        )
        assertThat(round.inputTypeForThisRound, `is`(nullValue()))
    }

    @Test
    fun `inputTypeForThisRound if tipps not set is tipps`() {
        val round = GameRound(
            round = 1,
            cardCount = 1,
            playerTippData = emptyList(),
            playerResultData = emptyList(),
            trumpType = TrumpType.NONE
        )
        assertThat(round.inputTypeForThisRound, `is`(InputType.TIPP))
    }

    @Test
    fun `inputTypeForThisRound if tipps set but result not set is result`() {
        val round = GameRound(
            round = 1,
            cardCount = 1,
            playerTippData = listOf(
                PlayerTippData(tipp = 0),
                PlayerTippData(tipp = 0),
                PlayerTippData(tipp = 0)
            ),
            playerResultData = emptyList(),
            trumpType = TrumpType.NONE
        )
        assertThat(round.inputTypeForThisRound, `is`(InputType.RESULT))
    }
}
