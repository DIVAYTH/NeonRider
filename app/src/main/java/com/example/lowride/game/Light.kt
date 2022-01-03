package com.example.lowride.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.lowride.R
import com.example.lowride.SizeDetermination

class Light(val resources: Resources):GameAction {

    override fun getModel(): Bitmap{
        return SizeDetermination.getSizeLight(BitmapFactory.decodeResource(resources, R.drawable.light))
    }
}