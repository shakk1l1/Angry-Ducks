package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.graphics.Paint

class ObstacleRectangle(val positionx:Double, val positiony:Double, val nx:Double, val ny:Double, val largeur:Double, val longueur:Double){
    private val segmentup = ObstacleSegment(positionx+nx*largeur,positiony+ny*largeur,longueur,nx,ny)
    private val segmentdown=ObstacleSegment(positionx-nx*largeur,positiony-ny*largeur,longueur,-nx,-ny)
    private val segmentleft=ObstacleSegment (positionx+ny*longueur,positiony-nx*longueur,largeur,ny,-nx)
    private val segmentright=ObstacleSegment (positionx-ny*longueur,positiony+nx*longueur,largeur,-ny,nx)
    val obstaclePaint = Paint()
    val obstaacles= arrayOf(segmentright,segmentleft,segmentdown,segmentup)
    init {
        obstaclePaint.strokeWidth = largeur.toFloat()
        obstaclePaint.color= Color.BLACK
    }

    fun draw(canvas: Canvas){

        canvas.drawLine((positionx+ny*(longueur-largeur/2)).toFloat(),(positiony-nx*(longueur-largeur/2)).toFloat(),(positionx-ny*(longueur-largeur/2)).toFloat(),(positiony+nx*(longueur-largeur/2)).toFloat(), obstaclePaint)

    }
}