package com.mygdx.game.factory

import com.mygdx.game.JsonSerialization
import com.mygdx.game.entity.Tile
import com.mygdx.game.utils.Utils

class TileFactory : EntityFactory {
    override fun create(content: ArrayList<String>): JsonSerialization {
        val xValue = content[0].filter { it.isDigit() || it == '.' }.toFloat()
        val yValue = content[1].filter { it.isDigit() || it == '.' }.toFloat()
        val widthValue = content[2].filter { it.isDigit() || it == '.' }.toFloat()
        val heightValue = content[3].filter { it.isDigit() || it == '.' }.toFloat()
        val occupationValue = Utils.getOccupation(content[4].substringAfter(": \"").dropLast(2))
        val groundLevelValue = content[5].filter { it.isDigit() || it == '.' }.toInt()

        val tile = Tile.create {
            x = xValue
            y = yValue
            width = widthValue
            height = heightValue
            occupation = occupationValue
            groundLevel = groundLevelValue
        }
        return tile
    }
}