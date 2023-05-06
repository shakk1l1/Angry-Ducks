package com.example.angryducks

import android.graphics.PointF
import kotlin.math.absoluteValue
import kotlin.math.pow

abstract class Objet(
    val mass: Double,
    var vitessex: Double,
    var vitessey: Double,
    val view: LevelView,
    protected val radius: Float
){

    protected var onscreen = true
    var coo = PointF()
    var colliding = false
    var collidingGroundCountDown = 0
    var collidingObjectCountDown = 0
    var collidingpointCountDown = 0

    fun getonscreen(): Boolean {return onscreen}

    open fun reset(){
        coo.x = 0f
        coo.y = 0f

    }
    open fun update2(interval:Double){
        if(onscreen) {// la "gravité"
            coo.x += (interval * vitessex).toFloat()
            coo.y += (interval * vitessey).toFloat()

            if (coo.x > view.screenWidth + 0f
                || coo.x < 0f
            ) {
                onscreen = false
            }
            else if ( coo.y < - 2000f) {
                onscreen = false
            }

            if (collidingGroundCountDown>0) {
                collidingGroundCountDown-=1
            }
            if (collidingObjectCountDown>0) {
                collidingObjectCountDown-=1
            }
            if (collidingpointCountDown>0) {
                collidingpointCountDown-=1
            }

            vitessey += (interval * 1000.0f).toFloat()

            //else if (coo.y + height* sin(orientation) + width * cos(orientation) >= (view.screenHeight - collision.groundheight).toDouble()) {
                //if (vitessey > 0.00001) {
                    //vitessey = -(collision.absorbtion * vitessey)
                    //vitessex = (vitessex * collision.absorbtion)


                //}
                //else{
                    //vitessey = 0.0
                //}
            //}

        }
    }
    open fun attributecollision(){

    }

    fun collisionSphereSphere(x1:Double,y1:Double,r1:Double,x2:Double,y2:Double,r2:Double) {
        val one = ((x1-x2).pow(2)+(y1-y2).pow(2)).pow(0.5)
        val two = r1+r2
        colliding =(one<two)

    }
    fun sphereCollideSphere(v1x:Double,v1y:Double,m1:Double,v2x:Double,v2y:Double,m2:Double,coef:Double, objet: Objet) {
        val vmoyx:Double = (m1*v1x+m2*v2x)/(m1+m2)
        val vmoyy:Double = (m1*v1y+m2*v2y)/(m1+m2)
        val dv1x=(1.0+coef)*(v1x-vmoyx)
        val dv1y=(1.0+coef)*(v1y-vmoyy)
        val dv2x=(1+coef)*(v2x-vmoyx)
        val dv2y=(1+coef)*(v2y-vmoyy)
        vitessex-=dv1x
        vitessey-=dv1y
        colliding = false
        //birdtexture.color = Color.GREEN
        collidingObjectCountDown=10
        objet.changeaftercoll(dv2x, dv2y)
        attributecollision()
    }

    open fun changeaftercoll(v2x:Double, v2y:Double) {
        vitessex-=v2x
        vitessey-=v2y
        collidingObjectCountDown=10
    }

    abstract fun touchinggrass(): Boolean


    abstract fun collideground()

    fun touchingobstaclesegment(positionx:Double,positiony:Double,longueur:Double,nx:Double,ny:Double) : Boolean{

        if (((positionx - coo.x) * nx + (positiony - coo.y) * ny).absoluteValue < radius){
            return ((positionx - coo.x) * ny - (positiony - coo.y) * nx).absoluteValue < ( longueur/2)
        }
        else{return false}
    }
    fun collideobstaclesegment(nx:Double, ny:Double, bloc: ObstacleRectangle){
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

    fun touchingobstaclepoint(positionx:Double,positiony:Double,rayon:Double) :Boolean{
        return((coo.x-positionx).pow(2)+(coo.y-positiony).pow(2) < (radius+rayon).pow(2))
    }

    fun collideobstaclepoint(positionx:Double,positiony:Double, bloc: ObstacleRectangle){
        val distance=((coo.x-positionx).pow(2)+(coo.y-positiony).pow(2)).pow(0.5)
        val nx=(coo.x-positionx)/distance
        val ny=(coo.y-positiony)/distance
        val prodvect=vitessex * nx+vitessey*ny
        if ((prodvect).absoluteValue<50) {
            val dvx : Double = prodvect * nx
            val dvy : Double = prodvect * ny
            vitessex = (vitessex - dvx)
            vitessey = (vitessey - dvy)
            //birdtexture.color = Color.BLUE
        }
        else {
            val dvx : Double = prodvect * nx * (1+Collision.absorbtion)
            val dvy : Double = prodvect * ny * (1+Collision.absorbtion)
            bloc.deteriorationdetect(vitessex, vitessey, mass)
            vitessex = (vitessex - dvx)
            vitessey = (vitessey - dvy)
            //birdtexture.color = Color.MAGENTA
            collidingpointCountDown = 3
            collidingGroundCountDown = 3
        }
    }

    fun getradius(): Float{return radius}
}