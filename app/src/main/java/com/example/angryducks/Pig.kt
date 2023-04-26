package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.angryducks.Objet
import kotlin.math.cos
import kotlin.math.sin


class Pig(view: LevelView, val massep : Float, val radius: Float, var xp : Float, var yp : Float, var vxp : Double, var vyp : Double, var orp : Float, var vangulp : Float)
    : Objet(massep,xp.toDouble(),yp.toDouble(),vxp,vyp,orp,vangulp, view) {
    var paintpig = Paint()
    init {
        paintpig.color = Color.parseColor("#2e6930")
        coo.x=xp
        coo.y = yp
        vitessex=vxp
        vitessey=vyp
        onscreen=true
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(
            xp, yp, radius, paintpig
        )
        canvas.drawText("${coo.x} + ${coo.y}",800f,500f,paintpig)

    }

    override fun update(interval: Double) {

    }

    fun kill() {


    }

    fun update() {

    }



}