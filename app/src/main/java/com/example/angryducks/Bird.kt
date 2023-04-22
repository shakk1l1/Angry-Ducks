package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF

class Bird (var view: LevelView, val pig: Pig, val obstacle: Obstacle, var groundheight: Float) : Objet(20.0, 0.0, 0.0, 0.0 ,0.0, 0.0, 0.0){
    var bird = PointF()
    var birdspeed = 1000f
    var birdspeedX = 1000f
    var birdspeedY = 1000f
    var birdonscreen = true
    val birdradius = 20f
    val birdtexture = Paint()
    var status_launched = false

    init {
        birdtexture.color = Color.RED
    }

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
            } else if (bird.y + birdradius > view.screenHeight
                || bird.y - birdradius < 0
            ) {
                birdonscreen = false
            }

        }
    }


    fun resetCanonBall() {
        birdonscreen = false
    }
    fun draw(canvas: Canvas) { //texture ou hitbox
        canvas.drawCircle(bird.x, bird.y, birdradius,
            birdtexture)
    }

    fun launch(diffx: Double, diffy: Double){
        bird.x = (birdradius)
        bird.y = (view.screenHeight - groundheight - 120f)
        vitessex= (-(2*diffx).toFloat()).toDouble()
        vitessey= (-(2*diffy).toFloat()).toDouble()
        birdonscreen = true
        status_launched = true
    }
}