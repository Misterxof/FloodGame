package com.mygdx.game.utils

import com.mygdx.game.TileOccupationType

class Utils {
    companion object {
        fun getOccupation(occupation: String): TileOccupationType {
            return when (occupation) {
                "FREE" -> TileOccupationType.FREE
                "FLOOD" -> TileOccupationType.FLOOD
                "PLAYER" -> TileOccupationType.PLAYER
                else -> throw Exception("Unknown occupation type $occupation!")
            }
        }
    }
}