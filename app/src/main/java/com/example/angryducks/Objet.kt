package com.example.angryducks

import android.content.Context
import android.graphics.PointF
import android.widget.Toast
import com.example.angryducks.collision.Companion.absorbtion
import com.example.angryducks.collision.Companion.groundheight
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

abstract class Objet (val mass : Float, var vitessex : Double, var vitessey : Double, var orientation : Double, var vangul : Double, var height: Float, var width: Float, val view: LevelView){

    var onscreen = true
    var coo = PointF()
    var colliding = false

    open fun update(interval:Double){

        // Si elle sorte de l'écran

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