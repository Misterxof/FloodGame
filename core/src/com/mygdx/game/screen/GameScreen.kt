package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.TimeUtils
import com.mygdx.game.FloodGame

open class GameScreen(val game: FloodGame) : Screen {
    private val dropImage: Texture = Texture(Gdx.files.internal("drop.png"))
    private val bucketImage: Texture = Texture(Gdx.files.internal("bucket.png"))
    private val camera: OrthographicCamera = OrthographicCamera()

    private lateinit var bucket: Rectangle
    private val raindrops: Array<Rectangle> = Array()
    private var lastDropTime: Long = 0
    private var dropsGathered = 0

    init {
        camera.setToOrtho(false, 800f, 480f)

        bucket = Rectangle()
        bucket.x = (800 / 2 - 64 / 2).toFloat()
        bucket.y = 20f
        bucket.width = 64f
        bucket.height = 64f

        spawnRainDrop()
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0.2f, 1f)

        camera.update()

        game.batch.projectionMatrix = camera.combined
        game.batch.begin()
        game.font.draw(game.batch, "Drops Collected: $dropsGathered", 0f, 480f)
        game.batch.draw(bucketImage, bucket.x, bucket.y)
        raindrops.forEach {
            game.batch.draw(dropImage, it.x, it.y)
        }
        game.batch.end()

        if (Gdx.input.isTouched) {
            val touchPos: Vector3 = Vector3()
            touchPos.set(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            camera.unproject(touchPos)
            bucket.x = touchPos.x - 64 / 2
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.deltaTime
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.deltaTime

        if (bucket.x < 0) bucket.x = 0f
        if (bucket.x > 800 - 64) bucket.x = 800f - 64f

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRainDrop()

        val i = raindrops.iterator()

        while (i.hasNext()) {
            val rainDrop: Rectangle = i.next()
            rainDrop.y -= 200 * Gdx.graphics.deltaTime
            if (rainDrop.y + 64 < 0) i.remove()

            if (rainDrop.overlaps(bucket)) {
                dropsGathered++;
                i.remove()
            }
        }


    }

    override fun show() {
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
        dropImage.dispose()
        bucketImage.dispose()
    }

    private fun spawnRainDrop() {
        val rainDrop = Rectangle()
        rainDrop.x = MathUtils.random(0f, 800f - 64f)
        rainDrop.y = 480f
        rainDrop.width = 64f
        rainDrop.height = 64f
        raindrops.add(rainDrop)
        lastDropTime = TimeUtils.nanoTime()
    }
}