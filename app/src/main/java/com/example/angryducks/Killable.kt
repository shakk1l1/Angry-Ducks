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
    fun kill(){             //Entrées:None ,Sorties:None
        killed = true       //Change la valeur de killed du killable lorsqu'il meurt
    }

    fun hdeterioration(hpinit: Int){            //Entrées:nombre de points de vie initial ,Sorties:None
        hp -= 100                               //Retire les points de dégats au killable et détermine son état
        if(hp <= 0){kill()}
        else if(hp<=hpinit/4){low()}
        else if(hp<=hpinit/2){mid()}
    }

    fun deteriorationdetect(vx: Double, vy: Double,m:Double, hpinit: Int){   //Entrées:vitesse de collision x et y, masse de l'objet ,Sorties:None
        val v = sqrt(vx.pow(2)+vy.pow(2))                           //Détermine la quantité de dégats subit par le killable lors d'un choc
        if(m*v>15000){hdeterioration(hpinit)}
        else if(m*v>10000){mdeterioration(hpinit)}
        else if (m*v>5000){ldeterioration(hpinit)}
    }

    fun ldeterioration(hpinit: Int){                //Entrées:nombre de points de vie initial ,Sorties:None
        hp -= 25                                    //Retire les points de dégats au killable et détermine son état
        if(hp <= 0){kill()}
        else if(hp<=hpinit/4){low()}
        else if(hp<=hpinit/2){mid()}
    }

    fun mdeterioration(hpinit: Int){                //Entrées:nombre de points de vie initial ,Sorties:None
        hp -= 50                                    //Retire les points de dégats au killable et détermine son état
        if(hp <= 0){kill()}
        else if(hp<=hpinit/4){low()}
        else if(hp<=hpinit/2){mid()}
    }

    fun low()

    fun mid()

}