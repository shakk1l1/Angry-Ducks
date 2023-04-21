package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.GREEN
import android.graphics.Paint
import android.graphics.RectF

class Ground (height : Float, val angle: Float, screenwidht: Float, screenheight: Float){

    val groundtexture = Paint()
    val ground = RectF(0f, screenheight-height, height, screenwidht)

    init {
        groundtexture.color = GREEN
    }
    fun draw(canvas: Canvas) {
        canvas.drawRect(ground, groundtexture)
    }
    fun setRect(){
    }
    fun Draw(){

    }
    fun Reset(){

    }
    fun istouching(){

    }
}
