package com.example.angryducks

import android.graphics.PointF
import com.example.angryducks.collision.Companion.absorbtion
import com.example.angryducks.collision.Companion.groundheight
import kotlin.math.cos
import kotlin.math.sin

abstract class Objet (val mass : Float, var vitessex : Double, var vitessey : Double, var orientation : Double, var vangul : Double, var height: Float, var width: Float, val view: LevelView){

    var onscreen = true
    var coo = PointF()

    fun update(interval:Double){

        // Si elle sorte de l'écran
        if(onscreen) {
            if (coo.y + height*sin(orientation) + width * cos(orientation) <= (view.screenHeight - groundheight).toDouble()) {
                vitessey += (interval * 1000.0f).toFloat()
            }
            coo.x += (interval * vitessex).toFloat()
            coo.y += (interval * vitessey).toFloat()

            if (coo.x > view.screenWidth + 50f
                || coo.x < -50f
            ) {
                onscreen = false
            }
            else if ( coo.y < - 2000f) {
                onscreen = false
            }
            else if (coo.y + height*sin(orientation) + width * cos(orientation) >= (view.screenHeight - groundheight).toDouble()) {
                if (vitessey > 0.0001) {
                    vitessey = -(absorbtion * vitessey)
                    vitessex = (vitessex * absorbtion)
                }
                else{
                    vitessey = 0.0
                }
            }
        }
    }

    fun istouching(object1 : Objet, object2 : Objet) {

    }


    fun applyforce(object1 : Objet, object2 : Objet) {

    }

    fun accelerate(object1 : Objet, force1 : Killable) {

    }

    fun touchinggrass(object1: Objet, ground1: Ground) {

    }

}