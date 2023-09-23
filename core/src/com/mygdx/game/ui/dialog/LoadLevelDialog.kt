package com.mygdx.game.ui.dialog

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.mygdx.game.entity.ColorFlood
import java.io.File
import kotlin.io.path.Path

class LoadLevelDialog(title: String, skin: Skin) : Dialog(title, skin) {
    private val dialogTable = Table()
    val table = Table()
    lateinit var scrollPane: ScrollPane

    fun createDialog(
        dialogEndedCallback: (isDialogOpen: Boolean) -> Unit,
    ) {
//        showFilesList(Gdx.files.localStoragePath)
        showFilesList("F:\\floodTest")

        table.debug = true
        table.align(Align.left or Align.top)
        println(this.contentTable.height)

        scrollPane = ScrollPane(table, skin)

        println(scrollPane.height)
        val saveLevelButton = TextButton("Load", skin, "default").apply {

            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                    this@LoadLevelDialog.hide()
                    dialogEndedCallback(false)
                }
            })
        }

        println("height ${Gdx.graphics.height}")
        dialogTable.padTop(16f)
        dialogTable.add(scrollPane).height(Gdx.graphics.height / 2f).width(Gdx.graphics.width / 3f)
        dialogTable.row()
        dialogTable.add(saveLevelButton).padTop(8f).colspan(2)

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

        if (currentPath.parent != null) {
            val label = Label("...", skin).apply {
                color = ColorFlood.DIRECTORY
                addListener(object : ClickListener() {
                    override fun clicked(event: InputEvent, x: Float, y: Float) {
                        if (tapCount >= 2) {
                            table.clear()
                            showFilesList(currentPath.parent.toString())
                        }
                    }
                })
            }

            table.add(label).left()
            table.row()
        }

        files.forEach {
            val card = ListCard(it.name, it.isDirectory, skin) {
                table.clear()
                showFilesList(it.path)
            }
            table.add(card).left()
            table.row()
//            val label = Label("${it.name}", skin)
//
//            label.addListener(object : ClickListener() {
//                override fun clicked(event: InputEvent, x: Float, y: Float) {
//                    if (it.isDirectory && tapCount >= 2) {
//                        table.clear()
//                        showFilesList(it.path)
//                    }
//                }
//            })
//
//            if (it.isDirectory)
//                label.color = ColorFlood.DIRECTORY
//            if (it.name.contains(".json"))
//                label.color = ColorFlood(51f, 255f, 51f, 1f).toRGB01()
//
//            table.add(label).left()
//            table.row()
        }
    }
}