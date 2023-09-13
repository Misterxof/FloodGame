package com.mygdx.game

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

fun main(args: Array<String>) {
    val config = Lwjgl3ApplicationConfiguration()
    config.setForegroundFPS(60)
    config.setTitle("Flood")
    config.setWindowedMode(1000, 1000)
    config.useVsync(true)
    val game = FloodGame(1000f, 1000f)
    Lwjgl3Application(game, config)
}