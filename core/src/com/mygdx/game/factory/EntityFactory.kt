package com.mygdx.game.factory

import com.mygdx.game.JsonSerialization
import com.mygdx.game.entity.Tile
import kotlin.reflect.KClass

interface EntityFactory {
    fun create(content: ArrayList<String>): JsonSerialization

    companion object {
        fun createFactory(type: KClass<*>): EntityFactory {
            return when (type.java) {
                Tile::class.java -> TileFactory()
                else -> throw Exception("Unknown type $type!")
            }
        }
    }
}