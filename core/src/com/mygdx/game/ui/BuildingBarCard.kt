package com.mygdx.game.ui

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.mygdx.game.entity.BuildingInfo

// !Icon background is same as card background
class BuildingBarCard(buildingInfo: BuildingInfo, skin: Skin) : Table() {

    init {
        val barBackground = skin.getDrawable("cardDrawableSelected")
        background = barBackground

        //this.debug = true
        this.add(buildingInfo.image).pad(8f, 8f, 8f, 8f)
        this.row()
        this.add(Label(buildingInfo.name, skin)).padBottom(8f)
        this.row()
        this.add(Label(buildingInfo.energyCost.toString(), skin)).padBottom(8f)
    }
}