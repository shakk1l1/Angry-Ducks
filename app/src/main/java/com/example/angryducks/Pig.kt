package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.angryducks.Objet


class Pig(val massep : Double, val radius: Float, var xp : Float, var yp : Float, var vxp : Double, var vyp : Double, var orp : Double, var vangulp : Double): Objet(massep,xp.toDouble(),yp.toDouble(),vxp,vyp,orp,vangulp) {
    var paintpig = Paint()
    init {
        paintpig.color = Color.GREEN
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(
            xp, yp, radius, paintpig
        )

    }

    fun kill() {


    }

    fun update() {

    }



}