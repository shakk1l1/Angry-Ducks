package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF

class Bird (view: LevelView, val pig: Pig, val obstacle: Obstacle, var groundheight: Float)
    : Objet(20f, 0.0, 0.0, 0.0 ,0.0, 20f, 20f, view){


    val birdradius = height
    val birdtexture = Paint()
    var status_launched = false

    init {
        birdtexture.color = Color.RED
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