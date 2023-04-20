package com.example.ba2projinfo

abstract class objet (val mass : Double, var positionx : Double, var positiony : Double, var vitessex : Double, var vitessey : Double, var orientation : Double, var vangul : Double):
    force {
    fun move(tick: Double) {
        positionx+=tick*vitessex
        positiony+=tick*vitessey
        orientation+=tick*vangul
    }

    fun istouching(object1 : objet, object2 : objet) {

    }


    fun applyforce(object1 : objet, object2 : objet) {

    }

    fun accelerate(object1 : objet, force1 : force) {

    }

    fun touchinggrass(object1: objet, ground1:ground) {

    }

}