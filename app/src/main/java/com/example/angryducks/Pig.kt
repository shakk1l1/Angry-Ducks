package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.angryducks.Objet
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin


class Pig(view: LevelView, val massep : Float, val radius: Float, var xp : Float, var yp : Float, var vxp : Double, var vyp : Double, var orp : Float, var vangulp : Float, val pigradius:Float)
    : Objet(massep,vxp,vyp,orp.toDouble(),vangulp.toDouble(), view) {
    var paintpig = Paint()

    init {
        paintpig.color = Color.parseColor("#2e6930")
        coo.x=xp
        coo.y = yp
        vitessex=vxp
        vitessey=vyp
        onscreen=true
        println(onscreen)
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(
            coo.x, coo.y, radius, paintpig
        )
        canvas.drawText("${coo.x} + ${coo.y}",800f,500f,paintpig)
        //onscreen=true
    }


    fun kill() {


    }

    fun changeaftercoll(v2x:Double, v2y:Double) {
        vitessex-=v2x
        vitessey-=v2y
    }

    override fun touchinggrass(): Boolean {
        var distancecarre:Double=0.0
        distancecarre= ((collision.m*coo.x+collision.p-coo.y).pow(2)/(1+collision.m.pow(2))).toDouble()
        return (distancecarre<pigradius.pow(2))
    }

    override fun Collideground() {
        var dvx : Double = vitessex * collision.nx * (1+collision.absorbtion)
        var dvy : Double = vitessey * collision.ny * (1+collision.absorbtion)
        vitessex = vitessex - dvx
        vitessey = vitessey - dvy
    }


}