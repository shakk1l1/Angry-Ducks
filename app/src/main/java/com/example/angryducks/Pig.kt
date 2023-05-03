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
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin


class Pig(view: LevelView, val massep : Float, val radius: Float, var xp : Float, var yp : Float,
          var vxp : Double, var vyp : Double, var orp : Float, var vangulp : Float, val pigradius:Float,
          override var hp: Int, override var killed: Boolean)

    : Objet(massep,vxp,vyp,orp.toDouble(),vangulp.toDouble(), view), Killable, Pigobserver{
    var paintpig = Paint()
    var death:Boolean = false

    init {
        paintpig.color = Color.parseColor("#056517")
        coo.x=xp
        coo.y = yp
        vitessex=vxp
        vitessey=vyp
        killed = false
        hp = 100
        onscreen=true
        //println(onscreen)
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
    fun draw(canvas: Canvas) {
        canvas.drawCircle(
            coo.x, coo.y, radius, paintpig
        )
        canvas.drawText("${coo.x} + ${vitessey}+${coo.y} ",800f,500f,paintpig)
        //onscreen=true
    }
    override fun update2(interval: Double) {
        super.update2(interval)
        if(killed && onscreen){
            onscreen = false
            view.pigleft -= 1
            death = true
        }
        if(!onscreen && !death){
            view.pigleft -=1
            death = true
        }
    }
    override fun changeaftercoll(v2x:Double, v2y:Double) {
        vitessex-=v2x
        vitessey-=v2y
        deteriorationdetect(vitessex, vitessey)
        collidingObjectCountDown=10
    }

    /*fun CollisionSphereSphere(x1:Double,y1:Double,r1:Double,x2:Double,y2:Double,r2:Double) {
        var one = ((x1-x2).pow(2)+(y1-y2).pow(2)).pow(0.5)
        var two = r1+r2
        collidingpig =(one<two)

    }*/

    /*fun SphereCollidePig(v1x:Double,v1y:Double,m1:Double,v2x:Double,v2y:Double,m2:Double,coef:Double, pig: Pig) {
        var vmoyx:Double = (m1*v1x+m2*v2x)/(m1+m2)
        var vmoyy:Double = (m1*v1y+m2*v2y)/(m1+m2)
        var dv1x=(1.0+coef)*(v1x-vmoyx)
        var dv1y=(1.0+coef)*(v1y-vmoyy)
        var dv2x=(1+coef)*(v2x-vmoyx)
        var dv2y=(1+coef)*(v2y-vmoyy)
        vitessex-=dv1x
        //v2x-=dv2x
        vitessey-=dv1y
        //v2y-=dv2y
        //println(vitessex)
        collidingpig = false
        //birdtexture.color = Color.GREEN
        pig.changeaftercoll(dv2x, dv2y)
    }*/



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
            else if(hp <= 50){mid()}
            else if(hp > 50){paintpig.color = Color.parseColor("#056517")}
        }

    }

}