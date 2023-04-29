package com.example.angryducks

interface Killable {

    var hp: Int

    var killed: Boolean
    fun kill(){
        killed = true
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