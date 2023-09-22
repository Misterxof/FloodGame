package com.mygdx.game.ui.dialog

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class SaveLevelDialog(title: String, skin: Skin) : Dialog(title, skin) {
    private val dialogTable = Table()

    fun createDialog(
        fileName: StringBuilder,
        dialogEndedCallback: (isDialogOpen: Boolean) -> Unit,
        write: (fileName: String) -> Boolean
    ) {
        val fileNameLabel = Label("File name: ", skin)
        val fileNameTextField = TextField("default", skin)

        fileNameTextField.setTextFieldListener { textField, c ->
            // code 8 is the "Backspace" keyboard button
            if (c.code == 8 && fileName.isNotEmpty()) {
                fileName.deleteCharAt(fileName.length - 1);
            } else
                fileName.append(c)
        }

        val saveLevelButton = TextButton("Save", skin, "default").apply {

            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                    val isSaved = write(fileName.toString())
                    if (isSaved) {
                        this@SaveLevelDialog.hide()
                        fileName.clear()
                        fileName.append("default")
                        dialogEndedCallback(false)
                    }
                }
            })
        }

        dialogTable.padTop(16f)
        dialogTable.add(fileNameLabel).padRight(8f).padLeft(8f)
        dialogTable.add(fileNameTextField).padRight(8f)
        dialogTable.row()
        dialogTable.add(saveLevelButton).padTop(8f).colspan(2)

        this.contentTable.add(dialogTable)
    }
}