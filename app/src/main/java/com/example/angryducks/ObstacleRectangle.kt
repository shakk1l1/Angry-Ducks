package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.graphics.Paint

class ObstacleRectangle(val positionx:Double, val positiony:Double, val nx:Double, val ny:Double, val largeur:Double, val longueur:Double,
                        override var hp: Int,
                        override var killed: Boolean
): Killable{
    private val segmentup = ObstacleSegment(positionx+nx*largeur/2,positiony+ny*largeur/2,longueur*2-largeur,nx,ny)
    private val segmentdown=ObstacleSegment(positionx-nx*largeur/2,positiony-ny*largeur/2,longueur*2-largeur,-nx,-ny)
    private val segmentleft=ObstacleSegment (positionx+ny*(longueur-largeur/2),positiony-nx*longueur/2,largeur,ny,-nx)
    private val segmentright=ObstacleSegment (positionx-ny*(longueur-largeur/2),positiony+nx*longueur/2,largeur,-ny,nx)
    private val pointupright=ObstaclePoint(positionx+nx*(largeur/2-2)+ny*(longueur-largeur/2-2),positiony+ny*(largeur/2-2)-nx*(longueur/2-2))
    private val pointupleft=ObstaclePoint(positionx+nx*(largeur/2-2)-ny*(longueur-largeur/2-2),positiony+ny*(largeur/2-1)+nx*(longueur/2-2))
    private val pointdownright=ObstaclePoint(positionx-nx*(largeur/2-2)+ny*(longueur-largeur/2-2),positiony-ny*(largeur/2-2)-nx*(longueur/2-2))
    private val pointdownleft=ObstaclePoint(positionx-nx*(largeur/2-2)-ny*(longueur-largeur/2-2),positiony-ny*(largeur/2-2)+nx*(longueur/2-2))
    val obstaclePaint = Paint()
    val obstaacles= arrayOf(segmentup,segmentleft,segmentdown,segmentright)
    val pooints= arrayOf(pointupleft,pointupright,pointdownleft,pointdownright)
    init {
        obstaclePaint.strokeWidth = largeur.toFloat()
        obstaclePaint.color= Color.BLACK
    }

    fun reset(){
        killed = false
        obstaclePaint.color = Color.BLACK
        hp = 20000
    }

    fun draw(canvas: Canvas){
        if(!killed) {
            canvas.drawLine(
                (positionx + ny * (longueur - largeur / 2)).toFloat(),
                (positiony - nx * (longueur - largeur / 2)).toFloat(),
                (positionx - ny * (longueur - largeur / 2)).toFloat(),
                (positiony + nx * (longueur - largeur / 2)).toFloat(),
                obstaclePaint
            )
        }
    }

    fun getkilled():Boolean {return killed}

    override fun low() {
        //obstaclePaint.color = Color.parseColor("#aaaaaa")
        obstaclePaint.color = Color.RED
    }

    override fun mid() {
        obstaclePaint.color = Color.parseColor("#808080")
    }
}