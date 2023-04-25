package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class Bird (view: LevelView, val pig: Pig, val obstacle: Obstacle, var groundheight: Float)
    : Objet(20f, 0.0, 0.0, 0.0 ,0.0, 20f, 20f, view){


    val birdradius = height
    val birdtexture = Paint()
    var status_launched = false

    init {
        birdtexture.color = Color.RED
    }

    override fun update(interval:Double){
        if(onscreen) {// la "gravité"
            if (coo.y + height* sin(orientation) + width * cos(orientation) <= (view.screenHeight - collision.groundheight).toDouble()) {
                vitessey += (interval * 1000.0f).toFloat()
            }
            coo.x += (interval * vitessex).toFloat()
            coo.y += (interval * vitessey).toFloat()

            if (coo.x > view.screenWidth + 50f
                || coo.x < -50f
            ) {
                onscreen = false
            }
            else if ( coo.y < - 2000f) {
                onscreen = false
            }
            else if (coo.y + height* sin(orientation) + width * cos(orientation) >= (view.screenHeight - collision.groundheight).toDouble()) {
                if (vitessey > 0.0001) {
                    vitessey = -(collision.absorbtion * vitessey)
                    vitessex = (vitessex * collision.absorbtion)
                    birdtexture.color = Color.RED

                }
                else{
                    vitessey = 0.0
                }
            }

        }
    }
    fun CollisionSphereSphere(x1:Double,y1:Double,r1:Double,x2:Double,y2:Double,r2:Double) {
        var one = ((x1-x2).pow(2)+(y1-y2).pow(2)).pow(0.5)
        var two = r1+r2
        colliding =(one<two)


    }
    fun BirdCollideBird(v1x:Double,v1y:Double,m1:Double,v2x:Double,v2y:Double,m2:Double,coef:Double) {
        var vmoyx:Double = (m1*v1x+m2*v2x)/(m1+m2)
        var vmoyy:Double = (m1*v1y+m2*v2y)/(m1+m2)
        var dv1x=(1+coef)*(v1x-vmoyx)
        var dv1y=(1+coef)*(v1y-vmoyy)
        //println("DOOOOOOOOOOOOOING IT")
        var dv2x=(1+coef)*(v2x-vmoyx)
        var dv2y=(1+coef)*(v2y-vmoyy)
        vitessex-=dv1x
        //v2x-=dv2x
        vitessey-=dv1y
        //v2y-=dv2y
        colliding = false
        birdtexture.color = Color.GREEN
    }
    /*
    fun update(interval:Double){
            // Si elle sorte de l'écran
        if(birdonscreen) {
            vitessey += (interval * 1000.0f).toFloat()
            bird.x += (interval * vitessex).toFloat()
            bird.y += (interval * vitessey).toFloat()

            if (bird.x + birdradius > view.screenWidth
                || bird.x - birdradius < 0
            ) {
                birdonscreen = false
            } else if ( bird.y - birdradius < 0) {
                birdonscreen = false
            }
            else if (bird.y + birdradius > view.screenHeight - groundheight){
            }

        }
    }

     */

    fun draw(canvas: Canvas) { //texture ou hitbox
        canvas.drawCircle(coo.x, coo.y, birdradius,
            birdtexture)
        canvas.drawText("${coo.x} + ${coo.y}",800f,500f, birdtexture)
    }

    fun launch(diffx: Double, diffy: Double){
        coo.x = (birdradius)
        coo.y = (view.screenHeight - groundheight - 120f)
        vitessex= (-(2*diffx).toFloat()).toDouble()
        vitessey= (-(2*diffy).toFloat()).toDouble()
        onscreen = true
        status_launched = true
    }
}