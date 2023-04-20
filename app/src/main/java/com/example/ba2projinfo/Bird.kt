package com.example.ba2projinfo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF

class Bird (var view: LevelView, val pig: Pig, val obstacle: Obstacle) : objet(20.0, 0.0, 0.0, 0.0 ,0.0, 0.0, 0.0){
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

    fun update(interval:Double){ //je te laisse changer ça mec

            /* Vérifions si la balle touche l'obstacle ou pas */
            if (positionx + birdradius > obstacle.obstacle.left &&
                positiony + birdradius > obstacle.obstacle.top &&
                positiony - birdradius < obstacle.obstacle.bottom) {
                collide(this, Obstacle)
                view.playObstacleSound()
            }
            // Si elle sorte de l'écran
            else if (canonball.x + canonballRadius > view.screenWidth
                || canonball.x - canonballRadius < 0) {
                canonballOnScreen = false
            }
            else if (canonball.y + canonballRadius > view.screenHeight
                || canonball.y - canonballRadius < 0) {
                canonballOnScreen = false
            }
            else if (canonball.x + canonballRadius > cible.cible.left
                && canonball.y + canonballRadius > cible.cible.top
                && canonball.y - canonballRadius < cible.cible.bottom) {
                cible.detectChoc(this)
                view.reduceTimeLeft()
            }
        }


    fun resetCanonBall() {
        canonballOnScreen = false
    }
    fun draw(canvas: Canvas) { //texture ou hitbox
        canvas.drawCircle(birds.x, birds.y, birdradius,
            birdtexture)
    }
}