package com.example.lowride.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.CountDownTimer
import com.example.lowride.R
import com.example.lowride.SizeDetermination

class Scene(val resources: Resources) : GameAction {
    lateinit var countDownTimer: CountDownTimer;
    val models: Array<Bitmap> = arrayOf(
        SizeDetermination.getSizeLayer(BitmapFactory.decodeResource(resources, R.drawable.scene_2)),
        SizeDetermination.getSizeLayer(BitmapFactory.decodeResource(resources, R.drawable.scene_3)),
        SizeDetermination.getSizeLayer(BitmapFactory.decodeResource(resources, R.drawable.scene_2)),
        SizeDetermination.getSizeLayer(BitmapFactory.decodeResource(resources, R.drawable.scene_1))
    )
    var mode: Int = -1
    val LINE_CENTR: Float = SizeDetermination.getCentre()
    val MOVE: Float = SizeDetermination.getMove()
    val LINE_TOP: Float = LINE_CENTR + MOVE
    val LINE_BOTTOM: Float = LINE_CENTR - MOVE
    var nigthMode: Boolean = false

    var x1: Float = 0f
    var x2: Float = SizeDetermination.getLayer()

    init {
        timerScene()
    }

    fun timerScene() {
        countDownTimer = object : CountDownTimer(120000, 30000) {
            override fun onTick(millisUntilFinished: Long) {
                mode++
                nigthMode = false
                when (mode) {
                    models.size -> mode = 0
                    3 -> nigthMode = true
                }
            }

            override fun onFinish() {
                cancel()
                start()
            }
        }
        countDownTimer.start()
    }

    override fun getModel(): Bitmap? {
        if (mode != -1) {
            return models.get(mode)
        }
        return null
    }
}