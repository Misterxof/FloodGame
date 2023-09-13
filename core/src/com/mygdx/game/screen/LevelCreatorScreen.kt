package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.mygdx.game.FloodGame
import com.mygdx.game.TileOccupationType
import com.mygdx.game.entity.Tile
import kotlin.random.Random

class LevelCreatorScreen (val game: FloodGame): Screen {
    val tiles = Array<Tile>()
    private val camera: OrthographicCamera = OrthographicCamera()
    private val stage = Stage()
    private val skin: Skin
    private val table = Table()

    init {
        camera.setToOrtho(false, game.width, game.height)
        skin = Skin(Gdx.files.internal("uiskin.json"))
        Gdx.input.inputProcessor = stage

        table.setFillParent(false)
        table.width = 200f
        table.height = 200f
        table.color = Color.WHITE
        //table.debug = true
        stage.addActor(table)

        for (i in 100 .. 800 step 100) {
            for (j in 100 .. 800 step 100) {
                val tile = Tile(j.toFloat(), i.toFloat(), 100f, 100f, TileOccupationType.FREE, Random.nextInt(1, 6))
                tiles.add(tile)
            }
        }
    }
    override fun show() {
    }

    override fun render(delta: Float) {
        game.shapeRenderer.projectionMatrix = camera.combined
        game.batch.projectionMatrix = camera.combined
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

        if (Gdx.input.justTouched()) {
            val touchPos: Vector3 = Vector3()
            touchPos.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            camera.unproject(touchPos)
            println("x = ${touchPos.x} y = ${touchPos.y}")
            val tile = findTile(touchPos)
            println(tile.toString())
            tile?.raiseGroundLevel()
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

    fun findTile(position: Vector3): Tile? {
        var resultTile: Tile? = null

        run breaking@ {
            tiles.forEach { tile ->
                if (tile.x <= position.x && position.x <= tile.x + tile.width && tile.y <= position.y && position.y <= tile.y + tile.height) {
                    resultTile = tile
                    return@breaking
                }
            }
        }

        return resultTile
    }
}