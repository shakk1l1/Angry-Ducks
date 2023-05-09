package com.example.angryducks

import kotlin.math.pow
import kotlin.math.sqrt

interface Killable {

    //----------------------------------------------------------------------------------------------
    // Variables init
    //----------------------------------------------------------------------------------------------

    var hp: Int
    var killed: Boolean
    //----------------------------------------------------------------------------------------------
    // Function
    //----------------------------------------------------------------------------------------------
    fun kill(){
        killed = true
    }

    fun hdeterioration(hpinit: Int){
        hp -= 100
        if(hp <= 0){kill()}
        else if(hp<=hpinit/4){low()}
        else if(hp<=hpinit/2){mid()}
    }

    fun deteriorationdetect(vx: Double, vy: Double,m:Double, hpinit: Int){
        val v = sqrt(vx.pow(2)+vy.pow(2))
        if(m*v>15000){hdeterioration(hpinit)}
        else if(m*v>10000){mdeterioration(hpinit)}
        else if (m*v>5000){ldeterioration(hpinit)}
    }

    fun ldeterioration(hpinit: Int){
        hp -= 25
        if(hp <= 0){kill()}
        else if(hp<=hpinit/4){low()}
        else if(hp<=hpinit/2){mid()}
    }

    fun mdeterioration(hpinit: Int){
        hp -= 50
        if(hp <= 0){kill()}
        else if(hp<=hpinit/4){low()}
        else if(hp<=hpinit/2){mid()}
    }

    fun low()

    fun mid()

}