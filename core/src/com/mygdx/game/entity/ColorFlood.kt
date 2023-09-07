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
}
