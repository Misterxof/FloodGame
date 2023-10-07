package com.mygdx.game.entity

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.mygdx.game.BuildingType

class BuildingInfo(val id: Int, var name: String, var image: Image, var energyCost: Int, var type: BuildingType) {
}