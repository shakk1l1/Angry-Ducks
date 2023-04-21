package com.example.angryducks

interface Force{

    fun changespeed(objet: Objet, vx: Double, vy: Double){
        objet.vitessex += vx
        objet.vitessey += vy
    }

    fun collide(object1 : Objet, object2 : Objet){
        object1.vitessey

    }

}