package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF

class Bird (var view: LevelView, val pig: Pig, val obstacle: Obstacle) : Objet(20.0, 0.0, 0.0, 0.0 ,0.0, 0.0, 0.0){
    var birds = PointF()
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
             if (positionx + birdradius > view.screenWidth
                || positionx - birdradius < 0) {
                birdonscreen = false
            }
            else if (positiony + birdradius > view.screenHeight
                || positiony - birdradius < 0) {
                birdonscreen = false
            }
        }


    fun resetCanonBall() {
        birdonscreen = false
    }
    fun draw(canvas: Canvas) { //texture ou hitbox
        canvas.drawCircle(birds.x, birds.y, birdradius,
            birdtexture)
    }
}