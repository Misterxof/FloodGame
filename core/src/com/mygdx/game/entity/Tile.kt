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

    constructor(builder: Builder) : this(
        builder.x,
        builder.y,
        builder.width,
        builder.height,
        builder.occupation,
        builder.groundLevel
    )

    fun getColor(): Color {
        return when (groundLevel) {
            1 -> ColorFlood.FIRST_GROUND_LEVEL
            2 -> ColorFlood.SECOND_GROUND_LEVEL
            3 -> ColorFlood.THIRD_GROUND_LEVEL
            4 -> ColorFlood.FOURTH_GROUND_LEVEL
            5 -> ColorFlood.FIFTH_GROUND_LEVEL
            else -> throw Exception("See level is not set or unsupported! Range is 1 - 5")
        }
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

    fun read(input: String) {

    }

//    fun writeToJson() : String {
//        "Tile : { \n \" "
//    }

    override fun toString(): String {
        return "Tile(x=$x, y=$y, width=$width, height=$height, occupation=$occupation, seeLevel=$groundLevel)"
    }

    companion object {
        fun create(init: Builder.() -> Unit) = Builder().apply(init).build()
    }

    class Builder  {
        var x = 0f
        var y = 0f
        var width = 0f
        var height = 0f
        var occupation = TileOccupationType.FREE
        var groundLevel = 0

        fun build() = Tile(this)
    }
}