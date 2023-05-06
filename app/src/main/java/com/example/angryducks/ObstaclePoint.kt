package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class ObstaclePoint (var positionx:Double, var positiony:Double, val rayon:Double){
    val pointPaint = Paint()
    init {
        pointPaint.color= Color.RED
    }
    fun draw(canvas: Canvas){
        canvas.drawCircle(
            positionx.toFloat(), positiony.toFloat(), rayon.toFloat(),
            pointPaint
        )
    }
}