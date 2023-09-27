package com.mygdx.game.ui

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.mygdx.game.entity.ColorFlood

class ListCard(
    name: String,
    isDirectory: Boolean,
    skin: Skin,
    onFileSelect: (String) -> Unit,
    onClickDirectory: () -> Unit
) : Table() {
    val card = skin.getDrawable("dialogDim")
    val selectedCard = skin.getDrawable("cardDrawableSelected")

    var backgroundColor = ColorFlood.CARD_BACKGROUND
    var isClicked = false

    init {
        val label = Label("$name", skin)

        this.debug = true
        this.align(Align.left or Align.top)

        background = card

        label.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                if (isDirectory && tapCount >= 2) {
                    onClickDirectory()
                }

                if (name.contains(".json")) {
                    if (!isClicked) {
                        onFileSelect(name)
                        background = selectedCard
                        isClicked = true
                    } else {
                        onFileSelect("")
                        background = card
                        isClicked = false
                    }
                }
            }
        })

        if (isDirectory)
            label.color = ColorFlood.DIRECTORY

        if (name.contains(".json"))
            label.color = ColorFlood(51f, 255f, 51f, 1f).toRGB01()

        this.add(label).left()
    }
}