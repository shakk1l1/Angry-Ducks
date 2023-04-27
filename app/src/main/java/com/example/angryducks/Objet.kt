package com.example.angryducks

import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.widget.Toast
import com.example.angryducks.collision.Companion.absorbtion
import com.example.angryducks.collision.Companion.groundheight
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

abstract class Objet (val mass : Float, var vitessex : Double, var vitessey : Double, var orientation : Double, var vangul : Double, val view: LevelView){

    var onscreen = true
    var coo = PointF()
    var colliding = false
    var collidingCountDown = 0

    fun update(interval:Double){
        if(onscreen) {// la "gravité"
            //if (coo.iy + height* sin(orientation) + width * cos(orientation) <= (view.screenHeight - collision.groundheight).toDouble()) {
            //vitessey += (interval * 1000.0f).toFloat()
            //}
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

            if (collidingCountDown>0) {
                collidingCountDown-=1
            }

            vitessey += (interval * 1000.0f).toFloat()

            //else if (coo.y + height* sin(orientation) + width * cos(orientation) >= (view.screenHeight - collision.groundheight).toDouble()) {
                //if (vitessey > 0.00001) {
                    //vitessey = -(collision.absorbtion * vitessey)
                    //vitessex = (vitessex * collision.absorbtion)


                //}
                //else{
                    //vitessey = 0.0
                //}
            //}

        }
    }

    fun istouching(object1 : Objet, object2 : Objet) {

    }


    fun applyforce(object1 : Objet, object2 : Objet) {

    }

    fun accelerate(object1 : Objet, force1 : Killable) {

    }

    abstract fun touchinggrass(): Boolean


    abstract fun Collideground()


}