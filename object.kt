package com.example.angryybeards

abstract class bloc (val mass : Double, var positionx : Double, var positiony : Double, var vitessex : Double, var vitessey : Double, var orientation : Double, var vangul : Double) {
    fun move(tick: Double) {
        positionx+=tick*vitessex
        positiony+=tick*vitessey
        orientation+=tick*vangul
    }

    fun istouching(object1 : object, object2 : object) {

    }

    fun collide(object1 : object, object2 : object){

    }

    fun applyforce(object1 : object, object2 : object) {

    }

    fun accelerate(object1 : object, force1 : force) {

    }

    fun touchinggrass(object, ground) {

    }

}