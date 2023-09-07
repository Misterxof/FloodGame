package com.mygdx.game.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Array
import com.mygdx.game.DropGame
import com.mygdx.game.TileOccupationType
import com.mygdx.game.entity.Tile
import kotlin.random.Random

class FloodGameScreen(val game: DropGame): Screen {
    val tiles = Array<Tile>()
    private val camera: OrthographicCamera = OrthographicCamera()

    init {
        camera.setToOrtho(false, 800f, 800f)

        for (i in 0 .. 700 step 100) {
            for (j in 0 .. 700 step 100) {
                val tile = Tile(i.toFloat(), j.toFloat(), 100f, 100f, TileOccupationType.FREE, Random.nextInt(1, 5))
                tiles.add(tile)
            }
        }
    }
    override fun show() {
    }

    override fun render(delta: Float) {
        game.shapeRenderer.projectionMatrix = camera.combined
        camera.update()


        tiles.forEach { tile ->
            game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            game.shapeRenderer.color = tile.getColor()
            game.shapeRenderer.rect(tile.x, tile.y, tile.width, tile.height)
            game.shapeRenderer.end()
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