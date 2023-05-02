package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.icu.text.RelativeDateTimeFormatter
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.angryducks.Objet
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin


class Pig(view: LevelView, val massep : Float, val radius: Float, var xp : Float, var yp : Float,
          var vxp : Double, var vyp : Double, var orp : Float, var vangulp : Float, val pigradius:Float,
          override var hp: Int, override var killed: Boolean)

    : Objet(massep,vxp,vyp,orp.toDouble(),vangulp.toDouble(), view), Killable, Pigobserver{
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
        hp = 100
        paintpig.color = Color.parseColor("#056517")
        killed = false
    }


    fun changeaftercoll(v2x:Double, v2y:Double) {
        vitessex-=v2x
        vitessey-=v2y
        deteriorationdetect(vitessex, vitessey)
        collidingObjectCountDown=10
    }

    override fun update2(interval: Double) {
        super.update2(interval)
        if(killed && onscreen){
            onscreen = false
            view.pigleft -= 1
        }
    }
    override fun touchinggrass(): Boolean {
        var distancecarre:Double=0.0
        distancecarre= ((collision.m*coo.x+(view.screenHeight-collision.groundheight)-coo.y).pow(2)/(1+collision.m.pow(2))).toDouble()
        return (distancecarre<pigradius.pow(2))
    }

    override fun Collideground() {
        if (vitessex * collision.nx+vitessey*collision.ny<50) {
            var dvx : Double = vitessex * collision.nx
            var dvy : Double = vitessey * collision.ny
            vitessex = (vitessex - dvx)*(1.0-collision.coefRoulement)
            vitessey = (vitessey - dvy)*(1.0-collision.coefRoulement)
        }
        else {
            var dvx : Double = vitessex * collision.nx * (1+collision.absorbtion)
            var dvy : Double = vitessey * collision.ny * (1+collision.absorbtion)
            vitessex = vitessex - dvx
            vitessey = vitessey - dvy
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
        //  ODO("show kill dialog")
    }

    override fun update(){
        GlobalScope.launch {
            repeat(3) {
                paintpig.color = Color.YELLOW
                delay(150)
                paintpig.color = Color.RED
                delay(150)
            }
            if(hp<=25){low()}
            else if(hp<=50){mid()}
            else if(hp > 50){paintpig.color = Color.parseColor("#056517")}
        }

    }

}