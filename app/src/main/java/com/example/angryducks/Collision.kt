package com.example.angryducks

import kotlin.math.pow

class collision (var bird: Bird, var pig: Pig, view: LevelView): Objectobservable{

    override val observers: ArrayList<Objectobserver> = ArrayList()

    companion object {
        val groundheight = 100f
        val absorbtion = 0.5f
        val m = 0.05
        val p = 1000f
        val nx = 0.0499376169
        val ny = 0.9987523389
        val coefRoulement = 0.01
    }


}
