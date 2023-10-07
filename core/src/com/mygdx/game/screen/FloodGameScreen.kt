package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.game.FloodGame
import com.mygdx.game.GameInputProcessor
import com.mygdx.game.TileOccupationType
import com.mygdx.game.entity.Tile
import com.mygdx.game.ui.BottomBar
import kotlin.random.Random

class FloodGameScreen(val game: FloodGame) : Screen {
    private val stage = Stage()
    private val skin: Skin
    private val bottomBar: Table
    val tiles = Array<Tile>()
    private val camera: OrthographicCamera = OrthographicCamera()
    private val gameInputProcessor: GameInputProcessor

    init {
        camera.setToOrtho(false, game.width, game.height)
        skin = Skin(Gdx.files.internal("uiskin.json"))
        gameInputProcessor = GameInputProcessor(camera)
        Gdx.input.inputProcessor = gameInputProcessor

        bottomBar = BottomBar(camera.viewportWidth, camera.viewportHeight / 6, skin)
        bottomBar.align(Align.bottom or Align.left)

        stage.addActor(bottomBar)

        for (i in 100..800 step 100) {
            for (j in 100..800 step 100) {
                val tile = Tile(j.toFloat(), i.toFloat(), 100f, 100f, TileOccupationType.FREE, Random.nextInt(1, 6))
                tiles.add(tile)
            }
        }
    }

    override fun show() {
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0.2f, 1f) // everything that is drawn will remain
        game.shapeRenderer.projectionMatrix = camera.combined
        game.batch.projectionMatrix = camera.combined
        stage.act(Gdx.graphics.deltaTime)
        camera.update()

        tiles.forEach { tile ->
            game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            game.shapeRenderer.color = tile.getColor()
            game.shapeRenderer.rect(tile.x, tile.y, tile.width, tile.height)
            game.shapeRenderer.end()
        }

        game.batch.begin()
        tiles.forEach { tile ->
            game.font.draw(game.batch, "${tile.groundLevel}", tile.x + 50, tile.y + 50)
        }
        game.batch.end()

        stage.draw()
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