package com.example.lowride.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.lowride.R
import com.example.lowride.SizeDetermination

class MusorCar(val resources: Resources) : GameAction {
    val models: Array<Bitmap> = arrayOf(
        SizeDetermination.getSizeMusorCar(BitmapFactory.decodeResource(resources, R.drawable.musor_car)),
        SizeDetermination.getSizeMusorCar(
            BitmapFactory.decodeResource(
                resources,
                R.drawable.musor_car_mode
            )
        )
    )
    var x1: Float = 0f
    var y1: Float = 0f

    var x2: Float = 0f
    var y2: Float = 0f
    var mode: Int = 0

    fun moveMusorCar1(v: Int) {
        x1 -= v * 2
    }

    fun moveMusorCar2(v: Int) {
        x2 -= v * 2
    }

    override fun getModel(): Bitmap {
        if (mode == models.size)
            mode = 0
        return models.get(mode++)
    }
}