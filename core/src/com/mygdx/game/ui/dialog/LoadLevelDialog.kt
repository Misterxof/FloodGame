package com.mygdx.game.ui.dialog

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.mygdx.game.JsonFileReader
import com.mygdx.game.JsonSerialization
import com.mygdx.game.ui.ListCard
import java.io.File
import kotlin.io.path.Path

class LoadLevelDialog(title: String, skin: Skin) : Dialog(title, skin) {
    private val dialogTable = Table()
    private val table = Table()

    private lateinit var scrollPane: ScrollPane
    private var currentPath = "F:\\floodTest"
    var currentSelectedFile: String = ""

    val setSelectedFile: (String) -> Unit = { fileName -> currentSelectedFile = fileName }

    fun createDialog(
        dialogEndedCallback: (isDialogOpen: Boolean, levelObjects: ArrayList<JsonSerialization>) -> Unit,
    ) {
//        showFilesList(Gdx.files.localStoragePath)
        showFilesList(currentPath)

        table.debug = true
        table.align(Align.left or Align.top)

        scrollPane = ScrollPane(table, skin)

        val loadLevelButton = TextButton("Load", skin, "default").apply {

            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {

                    if (currentSelectedFile.isNotBlank()) {
                        val levelObjects = JsonFileReader.readFile("$currentPath\\$currentSelectedFile")

                        this@LoadLevelDialog.hide()
                        dialogEndedCallback(false, levelObjects)
                    }
                }
            })
        }

        dialogTable.padTop(16f)
        dialogTable.add(scrollPane).height(Gdx.graphics.height / 2f).width(Gdx.graphics.width / 3f)
        dialogTable.row()
        dialogTable.add(loadLevelButton).padTop(8f).colspan(2)

        scrollPane.setScrollbarsVisible(true)
        this.contentTable.add(dialogTable)
    }

    fun showFilesList(path: String) {
        val files = File(path).listFiles()

        files.sortWith { file1, file2 ->
            return@sortWith when {
                file1.isDirectory && file2.isFile -> -1
                file1.isDirectory && file2.isFile -> 0
                file1.isFile && file2.isFile -> 0
                else -> 1
            }
        }
        val currentPath = Path(path)
        this.currentPath = path

        if (currentPath.parent != null) {
            val card = ListCard("...", true, skin, setSelectedFile) {
                table.clear()
                showFilesList(currentPath.parent.toString())
            }

            table.add(card).left().expandX().width(Gdx.graphics.width / 3f - 5f)
            table.row()
        }

        files.forEach {
            val card = ListCard(it.name, it.isDirectory, skin, setSelectedFile) {
                table.clear()
                showFilesList(it.path)
            }
            table.add(card).left().expandX().width(Gdx.graphics.width / 3f - 5f)
            table.row()
        }
    }
}