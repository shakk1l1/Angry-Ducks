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
): Killable,OnSizeChanged{
    //----------------------------------------------------------------------------------------------
    // Variables init
    //----------------------------------------------------------------------------------------------
    val hpinit = hp
    private val nx= sin(angle)  //Calcul du vecteur normal à l'obstacle
    private val ny= cos(angle)
    private val segmentup = ObstacleSegment(positionx+nx*largeur/2,positiony+ny*largeur/2,longueur,nx,ny)       //Initialisation des objets qui composent l'obstacle rectangle
    private val segmentdown=ObstacleSegment(positionx-nx*largeur/2,positiony-ny*largeur/2,longueur,-nx,-ny)
    private val segmentleft=ObstacleSegment (positionx+ny*(longueur/2),positiony-nx*longueur/2,largeur,ny,-nx)
    private val segmentright=ObstacleSegment (positionx-ny*(longueur/2),positiony+nx*longueur/2,largeur,-ny,nx)
    private val pointupright=ObstaclePoint(positionx+nx*(largeur/2-7)+ny*(longueur/2-7),positiony+ny*(largeur/2-7)-nx*(longueur/2-7),3.0)
    private val pointupleft=ObstaclePoint(positionx+nx*(largeur/2-7)-ny*(longueur/2-7),positiony+ny*(largeur/2-7)+nx*(longueur/2-7),3.0)
    private val pointdownright=ObstaclePoint(positionx-nx*(largeur/2-7)+ny*(longueur/2-7),positiony-ny*(largeur/2-7)-nx*(longueur/2-7), 3.0)
    private val pointdownleft=ObstaclePoint(positionx-nx*(largeur/2-7)-ny*(longueur/2-7),positiony-ny*(largeur/2-7)+nx*(longueur/2-7), 3.0)
    private var obstaclePaint = Paint()
    val obstaacles= arrayOf(segmentup,segmentleft,segmentdown,segmentright)         //Liste des composantes point
    val pooints= arrayOf(pointupleft,pointupright,pointdownleft,pointdownright)     //Liste des composantes segment

    //----------------------------------------------------------------------------------------------
    // Function
    //----------------------------------------------------------------------------------------------

    init {
        obstaclePaint.strokeWidth = largeur.toFloat()
        obstaclePaint.color= Color.BLACK
    }
    override fun set_posy(newy: Float){         //Entrées: newy, Sorties:None
        positiony += newy                       //Modifie la position des compasante de l'obstacle pour d'adapter à la taille de l'écran
        for (obstacle in obstaacles){
            obstacle.postiony +=newy
        }
        for (point in pooints){
            point.positiony +=newy
        }
    }

    fun reset(){                //Entrées:None ,Sorties:None
        killed = false          //Réinitialise l'obstacle
        obstaclePaint.color = Color.BLACK
        hp = hpinit
    }
    fun draw(canvas: Canvas){       //Entrées:canva ,Sorties:None
        if(!killed) {               //Affiche l'obstacle à l'écran
            canvas.drawLine(
                (positionx + ny * (longueur/ 2)).toFloat(),
                (positiony - nx * (longueur/ 2)).toFloat(),
                (positionx - ny * (longueur/ 2)).toFloat(),
                (positiony + nx * (longueur/ 2)).toFloat(),
                obstaclePaint
            )
        }
    }

    fun getkilled():Boolean {return killed}     //Entrées:None ,Sorties:retourne si l'obstacle à été détruit

    override fun low() {                                            //Entrées:None ,Sorties:None
        obstaclePaint.color = Color.parseColor("#aaaaaa")   //Modifie la couleur de l'objet lorsque celui-ci est endommagé
    }

    override fun mid() {                                            //Entrées:None ,Sorties:None
        obstaclePaint.color = Color.parseColor("#808080")  //Modifie la couleur de l'objet lorsque celui-ci est endommagé
    }
}