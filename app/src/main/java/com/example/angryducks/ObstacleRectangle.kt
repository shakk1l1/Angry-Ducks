package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class ObstacleRectangle(
    private val positionx:Double,
    private val positiony:Double,
    private val nx:Double,
    private val ny:Double,
    private val largeur:Double,
    private val longueur:Double,
    override var hp: Int,
    override var killed: Boolean
): Killable{
    //----------------------------------------------------------------------------------------------
    // Variables init
    //----------------------------------------------------------------------------------------------
    private val segmentup = ObstacleSegment(positionx+nx*largeur/2,positiony+ny*largeur/2,longueur*2-largeur,nx,ny)
    private val segmentdown=ObstacleSegment(positionx-nx*largeur/2,positiony-ny*largeur/2,longueur*2-largeur,-nx,-ny)
    private val segmentleft=ObstacleSegment (positionx+ny*(longueur-largeur/2),positiony-nx*longueur/2,largeur,ny,-nx)
    private val segmentright=ObstacleSegment (positionx-ny*(longueur-largeur/2),positiony+nx*longueur/2,largeur,-ny,nx)
    private val pointupright=ObstaclePoint(positionx+nx*(largeur/2-10)+ny*(longueur-largeur/2-10),positiony+ny*(largeur/2-5)-nx*(longueur/2-5),5.0)
    private val pointupleft=ObstaclePoint(positionx+nx*(largeur/2-10)-ny*(longueur-largeur/2-10),positiony+ny*(largeur/2-5)+nx*(longueur/2-5),5.0)
    private val pointdownright=ObstaclePoint(positionx-nx*(largeur/2-10)+ny*(longueur-largeur/2-10),positiony-ny*(largeur/2-5)-nx*(longueur/2-5), 5.0)
    private val pointdownleft=ObstaclePoint(positionx-nx*(largeur/2-10)-ny*(longueur-largeur/2-10),positiony-ny*(largeur/2-5)+nx*(longueur/2-5), 5.0)
    private var obstaclePaint = Paint()
    val obstaacles= arrayOf(segmentup,segmentleft,segmentdown,segmentright)
    val pooints= arrayOf(pointupleft,pointupright,pointdownleft,pointdownright)

    //----------------------------------------------------------------------------------------------
    // Function
    //----------------------------------------------------------------------------------------------

    init {
        obstaclePaint.strokeWidth = largeur.toFloat()
        obstaclePaint.color= Color.BLACK
    }

    fun reset(){
        killed = false
        obstaclePaint.color = Color.BLACK
        hp = 200
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
        obstaclePaint.color = Color.parseColor("#aaaaaa")
    }

    override fun mid() {
        obstaclePaint.color = Color.parseColor("#808080")
    }
}