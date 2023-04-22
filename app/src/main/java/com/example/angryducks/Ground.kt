package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Ground (var height : Float, val angle: Float, var screenHeight: Float, var screenWidth: Float, val view: LevelView){

    val groundtexture = Paint()
    val ground = RectF(0f, view.screenHeight-height, view.screenWidth-0f, view.screenHeight-0f)

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
    fun Draw(){

    }
    fun Reset(){

    }
    fun istouching(){

    }
}
