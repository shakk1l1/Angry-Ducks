package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.graphics.Paint

class ObstacleRectangle(val positionx:Double, val positiony:Double, val nx:Double, val ny:Double, val largeur:Double, val longueur:Double){
    private val segmentup : ObstacleSegment
    private val segmentdown : ObstacleSegment
    private val segmentleft : ObstacleSegment
    private val segmentright: ObstacleSegment
    val obstaclePaint = Paint()
    init {
        segmentup=ObstacleSegment(positionx+nx*largeur,positiony+ny*largeur,longueur,nx,ny)
        segmentdown=ObstacleSegment(positionx-nx*largeur,positiony-ny*largeur,longueur,-nx,-ny)
        segmentleft=ObstacleSegment (positionx+ny*longueur,positiony-nx*longueur,largeur,ny,-nx)
        segmentright=ObstacleSegment (positionx-ny*longueur,positiony+nx*longueur,largeur,-ny,nx)
    }

    fun draw(canvas: Canvas){
        obstaclePaint.strokeWidth = largeur.toFloat()
        obstaclePaint.color= Color.BLACK
        canvas.drawLine((positionx+ny*(longueur-largeur/2)).toFloat(),(positiony-nx*(longueur-largeur/2)).toFloat(),(positionx-ny*(longueur-largeur/2)).toFloat(),(positiony+nx*(longueur-largeur/2)).toFloat(), obstaclePaint)

    }
}