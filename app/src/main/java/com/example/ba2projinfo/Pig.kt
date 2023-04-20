package com.example.ba2projinfo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.compose.ui.graphics.Color.Companion.Blue


class Pig(val massep : Double, val radius: Float, var xp : Float, var yp : Float, var vxp : Double, var vyp : Double, var orp : Double, var vangulp : Double): objet(massep,xp.toDouble(),yp.toDouble(),vxp,vyp,orp,vangulp) {
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