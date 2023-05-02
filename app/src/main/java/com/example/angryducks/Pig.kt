package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.angryducks.Objet
import kotlin.coroutines.coroutineContext
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin


class Pig(view: LevelView, val massep : Float, val radius: Float, var xp : Float, var yp : Float,
          var vxp : Double, var vyp : Double, var orp : Float, var vangulp : Float, val pigradius:Float,
          override var hp: Int, override var killed: Boolean)

    : Objet(massep,vxp,vyp,orp.toDouble(),vangulp.toDouble(), view), Killable{
    var paintpig = Paint()
    init {
        paintpig.color = Color.parseColor("#056517")
        coo.x=xp
        coo.y = yp
        vitessex=vxp
        vitessey=vyp
        killed = false
        hp = 100
        onscreen=true
        println(onscreen)
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(
            coo.x, coo.y, radius, paintpig
        )
        canvas.drawText("${coo.x} + ${vitessey}+${coo.y} ",800f,500f,paintpig)
        //onscreen=true
    }

    override fun reset() {
        coo.x = xp
        coo.y = yp
        onscreen = true
        vitessex = 0.0
        vitessey = 0.0
    }


    fun changeaftercoll(v2x:Double, v2y:Double) {
        vitessex-=v2x
        vitessey-=v2y
        collidingObjectCountDown=10
    }

    override fun touchinggrass(): Boolean {
        var distancecarre:Double=0.0
        distancecarre= ((collision.m*coo.x-(view.screenHeight-collision.groundheight)+coo.y).pow(2)/(1+collision.m.pow(2))).toDouble()
        return (distancecarre<pigradius.pow(2))
    }

    override fun Collideground() {
        val prodvect=vitessex * collision.nx+vitessey*collision.ny
        if ((prodvect).absoluteValue<50) {
            var dvx : Double = prodvect * collision.nx
            var dvy : Double = prodvect * collision.ny
            vitessex = (vitessex - dvx)*(1.0-collision.coefRoulement)
            vitessey = (vitessey - dvy)*(1.0-collision.coefRoulement)

        }
        else {
            var dvx : Double = prodvect * collision.nx * (1+collision.absorbtion)
            var dvy : Double = prodvect * collision.ny * (1+collision.absorbtion)
            vitessex = (vitessex - dvx)
            vitessey = (vitessey - dvy)

            collidingGroundCountDown=2

        }
    }

    override fun low() {
        paintpig.color = Color.parseColor("#759116")
    }

    override fun mid() {
        paintpig.color = Color.parseColor("#de1a24")
    }

    override fun kill() {
        super.kill()
        TODO("show kill dialog")
    }

}