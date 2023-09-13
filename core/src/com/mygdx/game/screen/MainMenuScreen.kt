package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.game.DpiUtils
import com.mygdx.game.FloodGame

class MainMenuScreen(val game: FloodGame) : Screen {
    private val camera = OrthographicCamera()
    private val stage = Stage()
    private val table = Table()
    private val skin: Skin
    private var dp: Float

    init {
        camera.setToOrtho(false, game.width, game.height)
        Gdx.input.inputProcessor = stage
        dp = DpiUtils.getDp(Gdx.graphics)

        skin = Skin(Gdx.files.internal("uiskin.json"))

        table.setFillParent(true)
        //table.debug = true
        stage.addActor(table)

        val playButton = TextButton("Play",skin, "default").apply {
            width = 200f * dp
            height = 100f * dp
            setPosition(Gdx.graphics.width /2 - 100f, Gdx.graphics.height /2 - 10f)

            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x:  Float, y:Float){
                    game.screen = FloodGameScreen(game)
                    dispose()
                }
            })
        }

        val levelCreatorButton = TextButton("Create level",skin, "default").apply {
            width = 200f * dp
            height = 100f * dp
            setPosition(playButton.x, playButton.y - 120f)

            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x:  Float, y:Float){
                    game.screen = LevelCreatorScreen(game)
                    dispose()
                }
            })
        }

        table.addActor(playButton)
        table.addActor(levelCreatorButton)
    }

    override fun show() {

    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime)  // call all actors act method, for time actions
        stage.draw()       // uses its own batch to draw actors,stage has its own camera and batch
        camera.update()
        game.batch.projectionMatrix = camera.combined
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
    }

    override fun dispose() {
        stage.dispose()
    }
}