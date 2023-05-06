package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
class Ground(
    private var height: Float,
    var screenHeight: Float,
    var screenWidth: Float,
    view: LevelView
){
    //----------------------------------------------------------------------------------------------
    // Variables init
    //----------------------------------------------------------------------------------------------

    private val groundtexture = Paint()
    private val ground = RectF(0f, view.screenHeight-height, view.screenWidth-0f, view.screenHeight-0f)

    //----------------------------------------------------------------------------------------------
    // Function
    //----------------------------------------------------------------------------------------------
    init {
        groundtexture.color = Color.parseColor("#4caf50")
    }
    fun draw(canvas: Canvas) {
        canvas.drawRect(ground, groundtexture)
    }

    fun setRect() {
        ground.set(0f, screenHeight - height,
            screenWidth, screenHeight)
    }
}
