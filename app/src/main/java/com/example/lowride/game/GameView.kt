package com.example.lowride.game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.lowride.controllerAndMusic.OnSwipeTouchListener
import com.example.lowride.R
import com.example.lowride.SizeDetermination

class GameView(context: Context?) : View(context) {
    var scene: Scene
    var musorCar: MusorCar
    var car: Car
    var block: Block
    var light: Light
    var paint: Paint

    var pause: Boolean = false
    var gameOver:Boolean = false

    var kindDangerous: Int = 0

    var runnable: Runnable = Runnable {
        invalidate()
    }

    init {
        car = Car(resources)
        musorCar = MusorCar(resources)
        scene = Scene(resources)
        block = Block(resources)
        light = Light(resources)
        paint = Paint()
        paint.setColor(Color.WHITE)
        paint.textSize = SizeDetermination.getTextSize()
        paint.typeface = ResourcesCompat.getFont(context!!, R.font.wargames)
        swipeListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun swipeListener() {
        this.setOnTouchListener(object : OnSwipeTouchListener(context!!) {
            override fun onSwipeTop() {
                if (!pause) {
                    super.onSwipeTop()
                    if (car.y != scene.LINE_TOP) {
                        car.y += scene.MOVE
                    }
                }
            }

            override fun onSwipeBottom() {
                if (!pause) {
                    super.onSwipeBottom()
                    if (car.y != scene.LINE_BOTTOM) {
                        car.y -= scene.MOVE
                    }
                }
            }
        })
    }

    fun generationDangerous(): Int {
        musorCar.x1 = scene.x2 + SizeDetermination.getDistanceFirstDangerous()
        musorCar.x2 = scene.x2 + SizeDetermination.getDistanceSecondDangerous()
        block.x1 = scene.x2
        block.x2 = scene.x2
        when ((1..4).random()) {
            1 -> {
                when ((1..3).random()) {
                    1 -> musorCar.y1 = scene.LINE_TOP
                    2 -> musorCar.y1 = scene.LINE_CENTR
                    3 -> musorCar.y1 = scene.LINE_BOTTOM
                }
                return 1
            }
            2 -> {
                when ((1..3).random()) {
                    1 -> {
                        musorCar.y1 = scene.LINE_BOTTOM
                        musorCar.y2 = scene.LINE_CENTR
                    }
                    2 -> {
                        musorCar.y1 = scene.LINE_TOP
                        musorCar.y2 = scene.LINE_BOTTOM
                    }
                    3 -> {
                        musorCar.y1 = scene.LINE_TOP
                        musorCar.y2 = scene.LINE_CENTR
                    }
                }
                return 2
            }
            3 -> {
                when ((1..3).random()) {
                    1 -> {
                        musorCar.y1 = scene.LINE_BOTTOM
                        block.y1 = scene.LINE_TOP
                        block.y2 = scene.LINE_CENTR
                    }
                    2 -> {
                        musorCar.y1 = scene.LINE_TOP
                        block.y1 = scene.LINE_BOTTOM
                        block.y2 = scene.LINE_CENTR
                    }
                    3 -> {
                        block.y1 = scene.LINE_TOP
                        musorCar.y1 = scene.LINE_CENTR
                        block.y2 = scene.LINE_BOTTOM
                    }
                }
                return 3
            }
            4 -> {
                when ((1..3).random()) {
                    1 -> {
                        musorCar.y1 = scene.LINE_BOTTOM
                        musorCar.y2 = scene.LINE_TOP
                        block.y1 = scene.LINE_CENTR
                    }
                    2 -> {
                        musorCar.y1 = scene.LINE_CENTR
                        musorCar.y2 = scene.LINE_TOP
                        block.y1 = scene.LINE_BOTTOM
                    }
                    3 -> {
                        musorCar.y1 = scene.LINE_BOTTOM
                        musorCar.y2 = scene.LINE_CENTR
                        block.y1 = scene.LINE_TOP
                    }
                }
                return 4
            }
            else -> return 0
        }
    }

    fun gameOver(canvas: Canvas) {
        removeCallbacks(runnable)
        canvas.drawText(
            "GAME OVER",
            ((SizeDetermination.dWidth * 0.45).toFloat()),
            (SizeDetermination.dHeight / 2),
            paint
        )
        gameOver = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        car.move()
        car.acceleration()

        if (scene.x2 - car.v <= 0) {
            scene.x1 = 0f
            scene.x2 = SizeDetermination.getLayer()
            kindDangerous = generationDangerous()
        } else {
            scene.x1 -= car.v
            scene.x2 -= car.v
            when(kindDangerous){
                1 -> musorCar.moveMusorCar1(car.v)
                2 -> {
                    musorCar.moveMusorCar1(car.v)
                    musorCar.moveMusorCar2(car.v)
                }
                3 -> {
                    musorCar.moveMusorCar1(car.v)
                    block.moveBlock1(car.v)
                    block.moveBlock2(car.v)
                }
                4 -> {
                    musorCar.moveMusorCar1(car.v)
                    musorCar.moveMusorCar2(car.v)
                    block.moveBlock1(car.v)
                }
            }
        }

        val sceneModel: Bitmap? = scene.getModel()
        if (sceneModel != null) {
            canvas?.drawBitmap(sceneModel, scene.x1, 0f, null)
            canvas?.drawBitmap(sceneModel, scene.x2, 0f, null)
        }

        canvas?.drawBitmap(car.getModel(), car.x, car.y, null)

        if (scene.nigthMode) {
            canvas?.drawBitmap(
                light.getModel(),
                (car.x + car.with * 0.97).toFloat(),
                car.y + car.height / 3,
                null
            )
        }

        when (kindDangerous) {
            1 -> canvas?.drawBitmap(musorCar.getModel(), musorCar.x1, musorCar.y1, null)
            2 -> {
                canvas?.drawBitmap(musorCar.getModel(), musorCar.x1, musorCar.y1, null)
                canvas?.drawBitmap(musorCar.getModel(), musorCar.x2, musorCar.y2, null)
            }
            3 -> {
                canvas?.drawBitmap(musorCar.getModel(), musorCar.x1, musorCar.y1, null)
                val blockModel: Bitmap = block.getModel()
                canvas?.drawBitmap(blockModel, scene.x2, block.y1, null)
                canvas?.drawBitmap(blockModel, scene.x2, block.y2, null)
            }
            4 -> {
                canvas?.drawBitmap(musorCar.getModel(), musorCar.x1, musorCar.y1, null)
                canvas?.drawBitmap(musorCar.getModel(), musorCar.x2, musorCar.y2, null)
                canvas?.drawBitmap(block.getModel(), scene.x2, block.y1, null)
            }
        }

        canvas?.drawText(
            "score: " + car.s,
            (car.with * 0.2).toFloat(),
            (SizeDetermination.dHeight / 12),
            paint
        )

        if (car.y == musorCar.y1 && musorCar.x1 >= car.x && (car.x + car.with) >= musorCar.x1) {
            gameOver(canvas!!)
        } else if (car.y == musorCar.y2 && musorCar.x2 >= car.x && (car.x + car.with) >= musorCar.x2) {
            gameOver(canvas!!)

        } else if (car.y == block.y1 && block.x1 >= car.x && (car.x + car.with) >= block.x1) {
            gameOver(canvas!!)
        } else if (car.y == block.y2 && block.x2 >= car.x && (car.x + car.with) >= block.x2) {
            gameOver(canvas!!)
        } else {
            if (pause == false) {
                postDelayed(runnable, 40)
            }
        }
    }
}