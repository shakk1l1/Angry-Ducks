package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.cos
import kotlin.math.sin

class ObstacleRectangle(
    private var positionx:Double,
    private var positiony:Double,
    angle:Double,
    largeur:Double,
    private val longueur:Double,
    override var hp: Int,
    override var killed: Boolean
): Killable{
    //----------------------------------------------------------------------------------------------
    // Variables init
    //----------------------------------------------------------------------------------------------
    private val hpinit = hp
    private val nx= sin(angle)
    private val ny= cos(angle)
    private val segmentup = ObstacleSegment(positionx+nx*largeur/2,positiony+ny*largeur/2,longueur,nx,ny)
    private val segmentdown=ObstacleSegment(positionx-nx*largeur/2,positiony-ny*largeur/2,longueur,-nx,-ny)
    private val segmentleft=ObstacleSegment (positionx+ny*(longueur/2),positiony-nx*longueur/2,largeur,ny,-nx)
    private val segmentright=ObstacleSegment (positionx-ny*(longueur/2),positiony+nx*longueur/2,largeur,-ny,nx)
    private val pointupright=ObstaclePoint(positionx+nx*(largeur/2-7)+ny*(longueur/2-7),positiony+ny*(largeur/2-7)-nx*(longueur/2-7),3.0)
    private val pointupleft=ObstaclePoint(positionx+nx*(largeur/2-7)-ny*(longueur/2-7),positiony+ny*(largeur/2-7)+nx*(longueur/2-7),3.0)
    private val pointdownright=ObstaclePoint(positionx-nx*(largeur/2-7)+ny*(longueur/2-7),positiony-ny*(largeur/2-7)-nx*(longueur/2-7), 3.0)
    private val pointdownleft=ObstaclePoint(positionx-nx*(largeur/2-7)-ny*(longueur/2-7),positiony-ny*(largeur/2-7)+nx*(longueur/2-7), 3.0)
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
    fun onsizechanged(newy: Float){
        positiony += newy
        for (obstacle in obstaacles){
            obstacle.postiony +=newy
        }
        for (point in pooints){
            point.positiony +=newy
        }
    }

    fun reset(){
        killed = false
        obstaclePaint.color = Color.BLACK
        hp = hpinit
    }
    fun draw(canvas: Canvas){
        if(!killed) {
            canvas.drawLine(
                (positionx + ny * (longueur/ 2)).toFloat(),
                (positiony - nx * (longueur/ 2)).toFloat(),
                (positionx - ny * (longueur/ 2)).toFloat(),
                (positiony + nx * (longueur/ 2)).toFloat(),
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