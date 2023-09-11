package com.mygdx.game.entity

import com.badlogic.gdx.graphics.Color
import com.mygdx.game.TileOccupationType

class Tile(
    var x: Float,
    var y: Float,
    var width: Float,
    var height: Float,
    var occupation: TileOccupationType,
    var groundLevel: Int
) {

    fun getColor(): Color {
        when (groundLevel) {
            1 -> return ColorFlood(255f, 255f, 204f, 1f).toRGB01()
            2 -> return ColorFlood(255f, 255f, 128f, 1f).toRGB01()
            3 -> return ColorFlood(255f, 218f, 179f, 1f).toRGB01()
            4 -> return ColorFlood(255f, 181f, 102f, 1f).toRGB01()
            5 -> return ColorFlood(204f, 153f, 0f, 1f).toRGB01()
            else -> throw Exception("See level is not set or unsupported! Range is 1 - 5")
        }
        return ColorFlood(0f, 0f, 0f, 0f).getColor()
    }

    override fun toString(): String {
        return "Tile(x=$x, y=$y, width=$width, height=$height, occupation=$occupation, seeLevel=$groundLevel)"
    }


}