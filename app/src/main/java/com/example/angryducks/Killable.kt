package com.example.angryducks

import kotlin.math.pow
import kotlin.math.sqrt

interface Killable {

    var hp: Int

    var killed: Boolean
    fun kill(){
        killed = true
    }

    fun hdeterioration(){
        hp -= 270
        if(hp <= 0){kill()}
        else if(hp<=80){low()}
        else if(hp<=150){mid()}
    }

    fun deteriorationdetect(vx: Double, vy: Double,m:Double){
        val v = sqrt(vx.pow(2)+vy.pow(2))
        if(m*v>15000){hdeterioration()}
        else if(m*v>10000){mdeterioration()}
        else if (m*v>5000){ldeterioration()}
    }

    fun ldeterioration(){
        hp -= 90
        if(hp <= 0){kill()}
        else if(hp<=80){low()}
        else if(hp<=150){mid()}
    }

    fun mdeterioration(){
        hp -= 180
        if(hp <= 0){kill()}
        else if(hp<=80){low()}
        else if(hp<=150){mid()}
    }

    fun low()

    fun mid()

}