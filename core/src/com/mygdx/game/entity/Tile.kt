package com.mygdx.game.entity

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Json
import com.mygdx.game.JsonFileWriter
import com.mygdx.game.JsonSerialization
import com.mygdx.game.TileOccupationType
import java.io.PrintWriter

class Tile(
    var x: Float,
    var y: Float,
    var width: Float,
    var height: Float,
    var occupation: TileOccupationType,
    var groundLevel: Int
) : JsonSerialization {

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

    fun raiseGroundLevel() {
        groundLevel++
        if (groundLevel > 5)
            groundLevel = 1
    }

    override fun write(out: PrintWriter) {
        out.println(JsonFileWriter.addNumber("x", x))
        out.println(JsonFileWriter.addNumber("y", y))
        out.println(JsonFileWriter.addNumber("width", width))
        out.println(JsonFileWriter.addNumber("height", height))
        out.println(JsonFileWriter.addString("TileOccupationType", occupation.toString()))
        out.println(JsonFileWriter.addNumber("groundLevel", groundLevel))
    }

//    fun writeToJson() : String {
//        "Tile : { \n \" "
//    }

    override fun toString(): String {
        return "Tile(x=$x, y=$y, width=$width, height=$height, occupation=$occupation, seeLevel=$groundLevel)"
    }


}