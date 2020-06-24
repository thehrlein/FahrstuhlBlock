@file:JvmName("MaxRoundHelper")

package com.tobiapplications.fahrstuhlblock.ui_common.utils


fun getHighCardCount(cardCount: Int, playerCount: Int?): Int {
    if (playerCount == null) return 0
    return cardCount / playerCount
}
