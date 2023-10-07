package com.mygdx.game.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.mygdx.game.BuildingType
import com.mygdx.game.entity.BuildingInfo

class BottomBar(width: Float, height: Float, skin: Skin) : Table() {

    init {
        val barBackground = skin.getDrawable("cardDrawableSelected")
        val scrollContent = Table()
        val scrollPane = ScrollPane(scrollContent, skin)

        scrollContent.align(Align.bottom or Align.left)

//        this.debug = true
        this.setSize(width, height)
        background = barBackground

        val economyButton = TextButton("Economy", skin, "default").apply {

            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                }
            })
        }

        val attackButton = TextButton("Attack", skin, "default").apply {
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                }
            })
        }

        val specialButton = TextButton("Special", skin, "default").apply {

            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                }
            })
        }

        economyBuildingsList.forEach { building ->
            val buildingBarCard = BuildingBarCard(building, skin)
            scrollContent.add(buildingBarCard).left().height(height - 16).padLeft(8f).width(width / 8)
        }

        val tables = Table()
        tables.add(economyButton).left().width(128f)
        tables.add(attackButton).left().width(128f)
        tables.add(specialButton).left().width(128f)
        this.add(tables).left()
        this.row()
        this.add(scrollPane).padTop(8f).padBottom(8f).width(width).height(height - 16)
    }

    companion object {
        val economyBuildingsList = listOf<BuildingInfo>(
            BuildingInfo(1, "Node", Image(Texture(Gdx.files.internal("drop.png"))), 10, BuildingType.ECONOMY),
            BuildingInfo(2, "Generator", Image(Texture(Gdx.files.internal("bucket.png"))), 20, BuildingType.ECONOMY)
        )
    }
}