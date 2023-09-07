package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.game.DropGame

class MainMenuScreen(val game: DropGame) : Screen {
    private val camera = OrthographicCamera()

    init {
        camera.setToOrtho(false, 800f, 480f)
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0.2f, 1f)
        camera.update()
        game.batch.projectionMatrix = camera.combined

        game.batch.begin()
        game.font.draw(game.batch, "Welcome to Drop!", 100f, 150f)
        game.font.draw(game.batch, "Tap anywhere to begin", 100f, 100f)
        game.batch.end()

        if (Gdx.input.isTouched) {
//            game.screen = GameScreen(game)
            game.screen = FloodGameScreen(game)
            dispose()
        }
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
    }

    override fun dispose() {
    }
}