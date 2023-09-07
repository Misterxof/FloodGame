package com.mygdx.game

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

fun main(args: Array<String>) {
    val config = Lwjgl3ApplicationConfiguration()
    config.setForegroundFPS(60)
    config.setTitle("Flood")
    config.setWindowedMode(800, 800)
    config.useVsync(true)
    val game = DropGame()
    Lwjgl3Application(game, config)
}