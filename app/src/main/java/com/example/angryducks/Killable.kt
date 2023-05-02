package com.example.angryducks

import kotlin.math.pow
import kotlin.math.sqrt

interface Killable {

    var hp: Int

    var killed: Boolean
    fun kill(){
        killed = true
    }

    fun deteriorationdetect(vx: Double, vy: Double){
        val v = sqrt(vx.pow(2)+vy.pow(2))
        if(v>500){kill()}
        else if(v>250){hdeterioration()}
        else if (v>100){ldeterioration()}
    }

    fun ldeterioration(){
        hp -= 25
        if(hp <= 0){kill()}
        else if(hp>=25){low()}
        else if(hp>=50){mid()}
    }

    fun hdeterioration(){
        hp -= 50
        if(hp <= 0){kill()}
        else if(hp>=25){low()}
        else if(hp>=50){mid()}
    }

    fun low()

    fun mid()

}