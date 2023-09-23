package com.mygdx.game.entity

import com.badlogic.gdx.graphics.Color

class ColorFlood(
    var r: Float,
    var g: Float,
    var b: Float,
    var a: Float
) {
    fun toRGB01(): Color {
        return if (r in 0.0..255.0) Color(r / 255, g / 255, b / 255, a)
        else Color(r, g, b, a)
    }

    fun toRGB255(): Color {
        return if (r in 0.0..1.0) Color(r * 255, g * 255, b * 255, a)
        else Color(r, g, b, a)
    }

    fun getColor() = Color(r, g, b, a)

    companion object {
        val DIRECTORY = ColorFlood(255f, 219f, 77f, 1f).toRGB01()
        val CARD_BACKGROUND = ColorFlood(191f, 191f, 191f, 1f).toRGB01()
        val FIRST_GROUND_LEVEL = ColorFlood(255f, 255f, 204f, 1f).toRGB01()
        val SECOND_GROUND_LEVEL = ColorFlood(255f, 255f, 128f, 1f).toRGB01()
        val THIRD_GROUND_LEVEL = ColorFlood(255f, 218f, 179f, 1f).toRGB01()
        val FOURTH_GROUND_LEVEL = ColorFlood(255f, 181f, 102f, 1f).toRGB01()
        val FIFTH_GROUND_LEVEL = ColorFlood(204f, 153f, 0f, 1f).toRGB01()
    }
}
