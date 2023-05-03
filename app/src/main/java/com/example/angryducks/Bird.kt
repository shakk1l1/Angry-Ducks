package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.absoluteValue
import kotlin.math.pow

class Bird (view: LevelView, var groundheight: Float, val birdradius:Float)
    : Objet(20f, 0.0, 0.0, 0.0 ,0.0, view){


    private val birdtexture = Paint()
    var statuslaunched = false
    var collidingbird = false


    init {
        birdtexture.color = Color.RED
    }
    override fun reset(){
        onscreen = false
        birdtexture.color = Color.RED
        coo.x = 0f
        coo.y = 0f
    }
    fun draw(canvas: Canvas) { //texture ou hitbox
        canvas.drawCircle(coo.x, coo.y, birdradius,
            birdtexture)

    }

    override fun attributecollision() {
        birdtexture.color = Color.GREEN
    }

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
        birdtexture.color = Color.GREEN
        pig.changeaftercoll(dv2x, dv2y)
    }*/
    fun collisionSpherebird(x1:Double,y1:Double,r1:Double,x2:Double,y2:Double,r2:Double) {
        val one = ((x1-x2).pow(2)+(y1-y2).pow(2)).pow(0.5)
        val two = r1+r2
        collidingbird =(one<two)

    }
    fun birdCollideBird2(v1x:Double,v1y:Double,m1:Double,v2x:Double,v2y:Double,m2:Double,coef:Double, bird2: Bird) {
        val vmoyx: Double = (m1 * v1x + m2 * v2x) / (m1 + m2)
        val vmoyy: Double = (m1 * v1y + m2 * v2y) / (m1 + m2)
        val dv1x = (1.0 + coef) * (v1x - vmoyx)
        val dv1y = (1.0 + coef) * (v1y - vmoyy)
        val dv2x = (1 + coef) * (v2x - vmoyx)
        val dv2y = (1 + coef) * (v2y - vmoyy)
        vitessex -= dv1x
        //v2x-=dv2x
        vitessey -= dv1y
        //v2y-=dv2y
        //println(vitessex)
        collidingbird = false
        birdtexture.color = Color.GREEN
        collidingObjectCountDown=10
        bird2.changeaftercoll(dv2x, dv2y)
    }
    /*fun changeaftercoll(v2x:Double, v2y:Double) {
        vitessex-=v2x
        vitessey-=v2y
        collidingObjectCountDown=10
    }*/


    override fun touchinggrass(): Boolean {
        val distancecarre = ((Collision.m*coo.x-(view.screenHeight-Collision.groundheight)+coo.y).pow(2)/(1+Collision.m.pow(2)))
        return (distancecarre<birdradius.pow(2))
    }



    override fun collideground() {
        val prodvect=vitessex * Collision.nx+vitessey*Collision.ny
        if ((prodvect).absoluteValue<50) {
            val dvx : Double = prodvect * Collision.nx
            val dvy : Double = prodvect * Collision.ny
            vitessex = (vitessex - dvx)*(1.0-Collision.coefRoulement)
            vitessey = (vitessey - dvy)*(1.0-Collision.coefRoulement)
            birdtexture.color = Color.BLUE
        }
        else {
            val dvx : Double = prodvect * Collision.nx * (1+Collision.absorbtion)
            val dvy : Double = prodvect * Collision.ny * (1+Collision.absorbtion)
            vitessex = (vitessex - dvx)
            vitessey = (vitessey - dvy)
            birdtexture.color = Color.GRAY
            collidingGroundCountDown=2

        }
    }
    fun launch(diffx: Double, diffy: Double){
        coo.x = (birdradius)
        coo.y = (view.screenHeight - groundheight - 120f)
        vitessex= (-(2*diffx).toFloat()).toDouble()
        vitessey= (-(2*diffy).toFloat()).toDouble()
        onscreen = true
        statuslaunched = true
    }
}