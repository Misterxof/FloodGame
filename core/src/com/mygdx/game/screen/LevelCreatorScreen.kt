package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.TextInputListener
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.mygdx.game.FloodGame
import com.mygdx.game.JsonFileWriter
import com.mygdx.game.TileOccupationType
import com.mygdx.game.entity.Tile
import java.lang.StringBuilder
import kotlin.random.Random


class LevelCreatorScreen(val game: FloodGame) : Screen {
    val tiles = ArrayList<Tile>()
    private val camera: OrthographicCamera = OrthographicCamera()
    private val stage = Stage()
    private val skin: Skin
    private val table = Table()
    private val dialogTable = Table()
    private var fileName = StringBuilder("default")
    private var isDialogOpen = false

    init {
        camera.setToOrtho(false, game.width, game.height)
        skin = Skin(Gdx.files.internal("uiskin.json"))
        Gdx.input.inputProcessor = stage

        val dialog = Dialog("Save level", skin)

//        dialogScreen.isVisible = false
//        dialogScreen.setSize(stage.viewport.screenWidth.toFloat() / 2, stage.viewport.screenHeight.toFloat() / 2)
        //val successLabel = Label("Level is saved", skin)

        table.setFillParent(false)
        table.color = Color.WHITE
        table.setSize(200f, 200f)
        table.setPosition(stage.viewport.screenWidth - table.width, stage.viewport.screenY.toFloat())
        //table.align(Align.top or Align.center)
        table.debug = true  // outline for now
        stage.addActor(table)

        val saveLevelButton = TextButton("Save", skin, "default").apply {

            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                    val isSaved = JsonFileWriter.write("$fileName.json", tiles, "Tile")
                    if (isSaved) {
                        dialog.hide()
                        fileName.clear()
                        fileName.append("default")
                        println(fileName)
                        isDialogOpen = false
                    }
                }
            })
        }

        val fileNameLabel = Label("File name: ", skin)
        val fileNameTextField = TextField("default", skin)
        fileNameTextField.setTextFieldListener { textField, c ->
            if (c.code == 8 && fileName.isNotEmpty()) {
                fileName.deleteCharAt(fileName.length - 1);
            } else
                fileName.append(c)
        }
        dialogTable.padTop(16f)
        dialogTable.add(fileNameLabel).padRight(8f).padLeft(8f)
        dialogTable.add(fileNameTextField).padRight(8f)
        dialogTable.row()
        dialogTable.add(saveLevelButton).padTop(8f).colspan(2)

        dialog.contentTable.add(dialogTable)

        val openSaveDialogButton = TextButton("Save", skin, "default").apply {

            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                    dialog.show(stage)
                    isDialogOpen = true
                }
            })
        }

        val loadLevelButton = TextButton("Load level", skin, "default").apply {

            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                    // ! some conformation on success
                }
            })
        }

        table.add(openSaveDialogButton)

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

        if (Gdx.input.justTouched() && !isDialogOpen) {
            val touchPos: Vector3 = Vector3()
            touchPos.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            camera.unproject(touchPos)
            findTile(touchPos)?.raiseGroundLevel()
        }

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
        stage.dispose()
        skin.dispose()
    }

    fun findTile(position: Vector3): Tile? {
        var resultTile: Tile? = null

        run breaking@{
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