package com.example.angryducks

abstract class Objet (val mass : Double, var positionx : Double, var positiony : Double, var vitessex : Double, var vitessey : Double, var orientation : Double, var vangul : Double){
    fun move(tick: Double) {
        positionx+=tick*vitessex
        positiony+=tick*vitessey
        orientation+=tick*vangul
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