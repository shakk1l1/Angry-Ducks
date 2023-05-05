package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class ObstacleSegment (var postionx:Double,var postiony:Double,val longueur:Double, val nx:Double,val ny:Double){
    var segmentPaint = Paint()
    var largeur = 5
    init {
        segmentPaint.strokeWidth = largeur.toFloat()
        segmentPaint.color= Color.RED
    }
    fun draw(canvas: Canvas){
        canvas.drawLine((postionx+ny*(longueur-largeur/2)).toFloat(),(postiony-nx*(longueur-largeur/2)).toFloat(),(postionx-ny*(longueur-largeur/2)).toFloat(),(postiony+nx*(longueur-largeur/2)).toFloat(), segmentPaint)

    }

}

