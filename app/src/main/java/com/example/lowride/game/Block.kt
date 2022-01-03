package com.example.lowride.game

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.lowride.R
import com.example.lowride.SizeDetermination

class Block(val resources: Resources):GameAction {
    val models: Array<Bitmap> = arrayOf(
        SizeDetermination.getSizeBlock(BitmapFactory.decodeResource(resources, R.drawable.block_mode)),
        SizeDetermination.getSizeBlock(BitmapFactory.decodeResource(resources, R.drawable.block))
    )

    var x1:Float = 0f
    var y1: Float = 0f

    var x2:Float = 0f
    var y2: Float = 0f

    var mode:Int = 0

    fun moveBlock1(v: Int) {
        x1 -= v
    }

    fun moveBlock2(v: Int) {
        x2 -= v
    }

    override fun getModel(): Bitmap {
        if (mode == models.size)
            mode = 0
        return models.get(mode++)
    }
}