package com.mygdx.game.utils

import com.badlogic.gdx.Graphics
import kotlin.math.atan
import kotlin.math.sin

class DpiUtils private constructor(){
    companion object {
        fun getDp (graphics: Graphics): Float {
            when (graphics.ppiX) {
                in 0f..119f -> return 0.5f
                in 120f..139f -> return 0.75f
                in 140f..199f -> return 1f
                in 200f..279f -> return 1.5f
                in 280f..399f -> return 2f
                in 400f..559f -> return 2.5f
                in 560f..640f -> return 3f
                in 719f..1000f -> return 3.5f
            }

            return -1f
        }

        // let first value always be bigger than second one
        // with float common divisors it is not working (854,480 | 53,3)
        // * can do the same by reducing fraction
        fun aspectRatioFromResolution(width: Int, height: Int): Pair<Double, Double> {
            var diviser = -1

            for (i in 1..height) {
                if (width % i == 0 && height % i == 0)
                    diviser = maxOf(diviser, i)
            }

            return Pair((width / diviser).toDouble(), (height / diviser).toDouble())
        }

        // can be also done with next algorithm:
        // 1) diagonalPx = sqrt(widthPx^2 + heightPx^2)
        // 2) dpi = diagonalPx / diagonalInch
        fun calculateDpi(screenWidthPx: Int, screenHeightPx: Int, screenDiagonal: Float): Float {
//        val angle = atan(aspectRatioBasic.first / aspectRatioBasic.second)
            val aspectRatio = screenWidthPx.toDouble() / screenHeightPx.toDouble()
            val angle = atan(aspectRatio)   // angle of height cathetus
            val screenWidthInch =
                (sin(angle) * screenDiagonal.toDouble()).toFloat()    // cathetus = diagonal * sin(opposite angle in radians)
            return screenWidthPx / screenWidthInch
        }
    }
}