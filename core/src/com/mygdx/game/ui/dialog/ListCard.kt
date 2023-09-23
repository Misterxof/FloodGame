package com.mygdx.game.ui.dialog

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.mygdx.game.entity.ColorFlood

class ListCard(name: String, isDirectory: Boolean, skin: Skin, onClick: () -> Unit) : Table() {

    var backgroundColor = ColorFlood.CARD_BACKGROUND

    init {
        this.align(Align.left)
        this.debug = true
        val card = skin.getDrawable("cardDrawable")
        this.background = card
        println("w ${this.width} h ${this.height}")
        val label = Label("$name", skin)

        label.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                if (isDirectory && tapCount >= 2) {
                    onClick()
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