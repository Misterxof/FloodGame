package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.TimeUtils
import java.util.Iterator

open class Drop : ApplicationAdapter() {
    private lateinit var dropImage: Texture
    private lateinit var bucketImage: Texture
    private lateinit var camera: OrthographicCamera
    private lateinit var batch: SpriteBatch
    private lateinit var bucket:  Rectangle
    private val raindrops: Array<Rectangle> = Array()
    private var lastDropTime: Long = 0

    override fun create() {
        dropImage = Texture(Gdx.files.internal("drop.png"))
        bucketImage = Texture(Gdx.files.internal("bucket.png"))

        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 480f)

        batch = SpriteBatch()

        bucket = Rectangle()
        bucket.x = (800 / 2 - 64 / 2).toFloat()
        bucket.y = 20f
        bucket.width = 64f
        bucket.height = 64f

        spawnRainDrop()
    }

    override fun render() {
        ScreenUtils.clear(0f, 0f, 0.2f, 1f)

        camera.update()

        batch.projectionMatrix =  camera.combined
        batch.begin()
        batch.draw(bucketImage, bucket.x, bucket.y)
        raindrops.forEach {
            batch.draw(dropImage, it.x, it.y)
        }
        batch.end()

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
            val rainDrop : Rectangle = i.next()
            rainDrop.y -= 200 * Gdx.graphics.deltaTime
            if (rainDrop.y + 64 < 0) i.remove()

            if (rainDrop.overlaps(bucket)) {
                i.remove()
            }
        }


    }

    override fun dispose() {
        dropImage.dispose()
        bucketImage.dispose()
        batch.dispose()
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