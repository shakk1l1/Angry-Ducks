package com.example.ba2projinfo

interface force{

    fun changespeed(objet: objet, vx: Double, vy: Double){
        objet.vitessex += vx
        objet.vitessey += vy
    }

    fun collide(object1 : objet, object2 : objet){

    }

}