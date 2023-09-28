package com.mygdx.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.OrthographicCamera
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class GameInputProcessor(private val camera: OrthographicCamera) : InputProcessor {
    private val pressedKeys = TreeSet<Int>()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private fun onMultipleType(keycode: Int) {
        when (keycode) {
            Input.Keys.W -> {
                coroutineScope.launch {
                    while (pressedKeys.contains(Input.Keys.W)) {
                        delay(10)   // it is fast, oh boy
                        camera.translate(0f, 200f * Gdx.graphics.deltaTime)
                    }
                }
            }

            Input.Keys.S -> {
                coroutineScope.launch {
                    while (pressedKeys.contains(Input.Keys.S)) {
                        delay(10)
                        camera.translate(0f, -200f * Gdx.graphics.deltaTime)
                    }
                }
            }

            Input.Keys.A -> {
                coroutineScope.launch {
                    while (pressedKeys.contains(Input.Keys.A)) {
                        delay(10)
                        camera.translate(-200f * Gdx.graphics.deltaTime, 0f)
                    }
                }
            }

            Input.Keys.D -> {
                coroutineScope.launch {
                    while (pressedKeys.contains(Input.Keys.D)) {
                        delay(10)
                        camera.translate(200f * Gdx.graphics.deltaTime, 0f)
                    }
                }
            }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        pressedKeys.add(keycode)
        onMultipleType(keycode)

        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        pressedKeys.remove(keycode)
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }
}