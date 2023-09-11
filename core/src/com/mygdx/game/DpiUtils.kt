package com.mygdx.game

import kotlin.math.atan
import kotlin.math.sin

class DpiUtils {
    var screenWidthPx = 1920
    var screenHeightPx = 1080
    var screenDiagonal = 15.6f
    var aspectRatio = screenWidthPx.toDouble() / screenHeightPx.toDouble()

    // Like 16:9, 4:3
    var aspectRatioBasic = aspectRatioFromResolution(screenWidthPx, screenHeightPx)

    // Let first value always be bigger than second one
    // with float common dividers it is not working (854,480 | 53,3)
    // * can do the same by reducing fraction
    fun aspectRatioFromResolution(firstVal: Int, secondVal: Int): Pair<Double, Double> {
        var divider = -1

        for (i in 1..firstVal) {
            if (firstVal % i == 0 && secondVal % i == 0)
                divider = maxOf(divider, i)
        }

        return Pair((firstVal / divider).toDouble(), (secondVal / divider).toDouble())
    }

    fun calculateDpi(): Float {
//        val angle = atan(aspectRatioBasic.first / aspectRatioBasic.second)
        val angle = atan(aspectRatio)   // angle of height cathetus
        val screenWidthInch =
            (sin(angle) * screenDiagonal.toDouble()).toFloat()    // cathetus = diagonal * sin(opposite angle in radians)
        return screenWidthPx / screenWidthInch
    }
}

fun main() {
    val dpi = DpiUtils()
    println(DpiUtils().aspectRatioFromResolution(1920, 1080))
    println(DpiUtils().aspectRatioFromResolution(800, 480))
    println(DpiUtils().aspectRatioFromResolution(800, 600))
    println(DpiUtils().aspectRatioFromResolution(2048, 1536))
    println(dpi.calculateDpi())
}