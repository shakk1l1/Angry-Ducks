package com.example.angryducks

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.graphics.scale
import kotlin.math.absoluteValue
import kotlin.math.pow

class Bird (view: LevelView, var groundheight: Float, val birdradius:Float, private val masseb:Double)
    : Objet(masseb, 0.0, 0.0, view, birdradius){


    private val birdtexture = Paint()
    private var imagebird = BitmapFactory.decodeResource(view.resources,R.drawable.duck)
    var statuslaunched = false
    var collidingbird = false


    init {
        birdtexture.color = Color.RED
        imagebird = imagebird.scale((2*birdradius).toInt(),(2*birdradius).toInt())

    }
    override fun reset(){
        onscreen = false
        birdtexture.color = Color.RED
        coo.x = 0f
        coo.y = 0f
    }
    fun draw(canvas: Canvas) { //texture ou hitbox
        if (onscreen) {
            canvas.drawCircle(
                coo.x, coo.y, birdradius,
                birdtexture
            )
            //canvas.drawBitmap(imagebird,coo.x-birdradius,coo.y-birdradius,null)

        }
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
        vitessey -= dv1y
        collidingbird = false
        birdtexture.color = Color.GREEN
        collidingObjectCountDown=10
        bird2.changeaftercoll(dv2x, dv2y)
    }

    //fun birdCollideBird2(v1x:Double,v1y:Double, x1:Double, y1:Double,m1:Double,v2x:Double,v2y:Double,x2:Double, y2:Double,m2:Double,coef:Double, bird2: Bird) {
    //        val distance = ((x1-x2).pow(2)+(y1-y2).pow(2)).pow(0.5)
    //        val vecteurcollisionx: Double = (x1-x2)/distance
    //        val vecteurcollisiony: Double = (y1-y2)/distance
    //        val vmoyx: Double = (m1 * v1x + m2 * v2x) / (m1 + m2)
    //        val vmoyy: Double = (m1 * v1y + m2 * v2y) / (m1 + m2)
    //        val vmoycoll=vmoyx*vecteurcollisionx+vmoyy*vecteurcollisiony
    //        val vmoycollx=vmoycoll*vecteurcollisionx
    //        val vmoycolly=vmoycoll*vecteurcollisiony
    //        val prodvect1=v1x * vecteurcollisionx+v1y*vecteurcollisiony
    //        val prodvect2=v2x * vecteurcollisionx+v2y*vecteurcollisiony
    //        val dv1x = (1.0 + coef) * (prodvect1 * vecteurcollisionx - vmoycollx)
    //        val dv1y = (1.0 + coef) * (prodvect1 * vecteurcollisiony - vmoycolly)
    //        val dv2x = (1 + coef) * (prodvect2 * vecteurcollisionx - vmoyx)
    //        val dv2y = (1 + coef) * (prodvect2 * vecteurcollisiony - vmoyy)
    //
    //        vitessex -= dv1x
    //        vitessey -= dv1y
    //        collidingbird = false
    //        birdtexture.color = Color.GREEN
    //        collidingObjectCountDown=10
    //        bird2.changeaftercoll(dv2x, dv2y)
    //    }
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
            collidingGroundCountDown=3
        }
    }

/*
    override fun touchingobstaclesegment(positionx:Double,positiony:Double,longueur:Double,nx:Double,ny:Double): Boolean {
            if (((positionx - coo.x) * nx + (positiony - coo.y) * ny).absoluteValue < birdradius){
                return ((positionx - coo.x) * ny - (positiony - coo.y) * nx).absoluteValue < ( longueur/2)
            }
            else{return false}
    }

    override fun collideobstaclesegment(nx: Double, ny: Double, bloc:ObstacleRectangle) {
        val prodvect=vitessex * nx+vitessey*ny
        if ((prodvect).absoluteValue<100) {
            val dvx : Double = prodvect * nx
            val dvy : Double = prodvect * ny
            vitessex = (vitessex - dvx)*(1.0-Collision.coefRoulement)
            vitessey = (vitessey - dvy)*(1.0-Collision.coefRoulement)

        }
        else {
            val dvx: Double = prodvect * nx * (1 + Collision.absorbtion)
            val dvy: Double = prodvect * ny * (1 + Collision.absorbtion)
            bloc.deteriorationdetect(vitessex, vitessey, mass)
            vitessex = (vitessex - dvx)
            vitessey = (vitessey - dvy)

            collidingGroundCountDown = 3
        }
    }

    override fun touchingobstaclepoint(positionx: Double, positiony: Double, rayon:Double): Boolean {
        return((coo.x-positionx).pow(2)+(coo.y-positiony).pow(2) < (birdradius+rayon).pow(2))
    }

    override fun collideobstaclepoint(positionx:Double,positiony:Double, bloc: ObstacleRectangle) {
        val distance=((coo.x-positionx).pow(2)+(coo.y-positiony).pow(2)).pow(0.5)
        val nx=(coo.x-positionx)/distance
        val ny=(coo.y-positiony)/distance
        val prodvect=vitessex * nx+vitessey*ny
        if ((prodvect).absoluteValue<50) {
            val dvx : Double = prodvect * nx
            val dvy : Double = prodvect * ny
            vitessex = (vitessex - dvx)
            vitessey = (vitessey - dvy)
            birdtexture.color = Color.BLUE
        }
        else {
            val dvx : Double = prodvect * nx * (1+Collision.absorbtion)
            val dvy : Double = prodvect * ny * (1+Collision.absorbtion)
            bloc.deteriorationdetect(vitessex, vitessey, mass)
            vitessex = (vitessex - dvx)
            vitessey = (vitessey - dvy)
            birdtexture.color = Color.MAGENTA
            collidingpointCountDown = 3
        }
    }

 */


    fun launch(diffx: Double, diffy: Double){
        coo.x = (birdradius)
        coo.y = (view.screenHeight - groundheight - 120f)
        vitessex= (-(3*diffx))
        vitessey= (-(3*diffy))
        onscreen = true
        statuslaunched = true
    }
}