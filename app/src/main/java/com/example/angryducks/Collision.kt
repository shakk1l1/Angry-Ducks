package com.example.angryducks

import kotlin.math.pow

class collision (var bird: Bird, var pig: Pig, view: LevelView): Objectobservable{

    override val observers: ArrayList<Objectobserver> = ArrayList()

    companion object {
        val groundheight = 100f
        val absorbtion = 0.5f
        val m = 0f
        val p = 1000f
        val nx = 0.0
        val ny = 1.0
        val coefRoulement = 0.02
    }


}
