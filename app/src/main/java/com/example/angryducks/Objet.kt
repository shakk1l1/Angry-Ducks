package com.example.angryducks

import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.widget.Toast
import com.example.angryducks.collision.Companion.absorbtion
import com.example.angryducks.collision.Companion.groundheight
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

abstract class Objet (val mass : Float, var vitessex : Double, var vitessey : Double, var orientation : Double, var vangul : Double, val view: LevelView){

    var onscreen = true
    var coo = PointF()
    var collidingpig = false
    var collidingGroundCountDown = 0
    var collidingObjectCountDown = 0

    open fun reset(){
        coo.x = 0f
        coo.y = 0f

    }
    open fun update2(interval:Double){
        if(onscreen) {// la "gravité"
            //if (coo.iy + height* sin(orientation) + width * cos(orientation) <= (view.screenHeight - collision.groundheight).toDouble()) {
            //vitessey += (interval * 1000.0f).toFloat()
            //}
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

    fun CollisionSpherePig(x1:Double,y1:Double,r1:Double,x2:Double,y2:Double,r2:Double) {
        var one = ((x1-x2).pow(2)+(y1-y2).pow(2)).pow(0.5)
        var two = r1+r2
        collidingpig =(one<two)
    }
    fun SphereCollidePig(v1x:Double,v1y:Double,m1:Double,v2x:Double,v2y:Double,m2:Double,coef:Double, pig: Pig) {
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
        attributecollision()
    }

    open fun changeaftercoll(v2x:Double, v2y:Double) {
        vitessex-=v2x
        vitessey-=v2y
        collidingObjectCountDown=10
    }
    fun istouching(object1 : Objet, object2 : Objet) {

    }


    fun applyforce(object1 : Objet, object2 : Objet) {

    }

    fun accelerate(object1 : Objet, force1 : Killable) {

    }

    abstract fun touchinggrass(): Boolean


    abstract fun Collideground()



}