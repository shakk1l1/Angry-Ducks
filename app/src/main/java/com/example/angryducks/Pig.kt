package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.angryducks.Objet


class Pig(view: LevelView, val massep : Float, val radius: Float, var xp : Float, var yp : Float, var vxp : Double, var vyp : Double, var orp : Float, var vangulp : Float)
    : Objet(massep,xp.toDouble(),yp.toDouble(),vxp,vyp,orp,vangulp, view) {
    var paintpig = Paint()
    init {
        paintpig.color = Color.parseColor("#2e6930")
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