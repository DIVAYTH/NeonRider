package com.example.lowride.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.lowride.R
import com.example.lowride.SizeDetermination

class Car(val resources: Resources):GameAction {
    val models: Array<Bitmap> = arrayOf(
        SizeDetermination.getSizeCar(BitmapFactory.decodeResource(resources, R.drawable.car_1)),
        SizeDetermination.getSizeCar(BitmapFactory.decodeResource(resources, R.drawable.car_2)),
        SizeDetermination.getSizeCar(BitmapFactory.decodeResource(resources, R.drawable.car_3))
    )
    var mode: Int = 0
    var v: Int = 40
    var s: Int = 0
    var sAcceleration = 10000
    var y: Float
    var x: Float
    var with: Int
    var height:Int
    var limit: Float = SizeDetermination.getCentre()

    init {
        with = models.get(0).width
        height = models.get(0).height
        y = SizeDetermination.getCentre()
        x = SizeDetermination.getMarginCar()
    }

//    fun setTurnLimit(turn: Float) {
//        limit = turn
//    }

//    fun turn() {
//        if (y < limit) {
//            y += limit / 4
//        } else if (y > limit) {
//            y -= limit / 4
//        }
//    }

    fun acceleration(){
        if (s >= sAcceleration && v != 70){
            v += 5
            sAcceleration += sAcceleration
        }
    }

    fun move() {
        s += v
    }

    override fun getModel(): Bitmap {
        if (mode == models.size)
            mode = 0
        return models.get(mode++)
    }
}