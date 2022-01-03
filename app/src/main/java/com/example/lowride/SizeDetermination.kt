package com.example.lowride

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.view.ViewGroup

/**
 * Синглтон определяющий размер для всех объектов
 */
object SizeDetermination {
    var dWidth: Float = 0.0f
    var dHeight: Float = 0f

    // Получаем расширение экрана для адаптации всех объектов
    fun getSizeDisplay(resources: Resources, context: Context) {
        val metrics = context.resources.displayMetrics
        dWidth = metrics.widthPixels.toFloat()
        dHeight = (metrics.heightPixels - getStatusBarHeight(resources)).toFloat()
    }

    fun getStatusBarHeight(resources: Resources): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    /**
     * MainActivity
     */
    fun getCarPositionLeft(): Float {
        return -dWidth / 2
    }

    fun getCarPositionRight(): Float {
        return dWidth + dWidth / 2
    }

    fun getYline2Margin(): Float {
        return (dHeight * 0.11).toFloat()
    }

    fun getYline3Margin(): Float {
        return (dHeight * 0.21).toFloat()
    }


    /**
     * StartGameActivity
     */
    fun getGetPauseButton(bm: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bm, (dWidth / 10).toInt(), (dHeight / 8).toInt(), true)
    }

    fun getGetLPPauseButton(): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams((dWidth / 10).toInt(), (dHeight / 10).toInt())
    }

    /**
     * Game
     */
    fun getLayer(): Float {
        return dWidth
    }

    fun getTextSize(): Float {
        return (dHeight * 0.05).toFloat()
    }

    fun getSizeLayer(bm: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bm, dWidth.toInt(), dHeight.toInt(), true)
    }

    fun getSizeCar(bm: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(
            bm,
            (dWidth * 0.125).toInt(),
            (dHeight * 0.175).toInt(),
            true
        )
    }

    fun getSizeMusorCar(bm: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(
            bm,
            (dWidth * 0.125).toInt(),
            (dHeight * 0.13).toInt(),
            true
        )
    }

    fun getSizeBlock(bm: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(
            bm,
            (dWidth * 0.075).toInt(),
            (dHeight * 0.14).toInt(),
            true
        )
    }

    fun getSizeLight(bm: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(
            bm,
            (dWidth * 0.1).toInt(),
            (dHeight * 0.075).toInt(),
            true
        )
    }

    fun getCentre(): Float {
        return (dHeight * 0.61).toFloat()
    }

    fun getMove(): Float {
        return (dHeight * 0.1).toFloat()
    }

    fun getMarginCar(): Float {
        return ((dWidth * 0.1) / 2).toFloat()
    }

    fun getDistanceFirstDangerous(): Int {
        return ((dWidth * 0.125).toInt()..(dWidth * 0.125).toInt() * 2).random()
    }

    fun getDistanceSecondDangerous(): Int {
        return ((dWidth * 0.125).toInt() * 4..(dWidth * 0.125).toInt() * 6).random()
    }
}