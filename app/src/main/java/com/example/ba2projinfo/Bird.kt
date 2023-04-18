package com.example.ba2projinfo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF

class Bird (var view: LevelView, val obstacle: Obstacle, val cible: Cible) {
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

    fun launch(angle: Double) {
        birds.x = birdradius
        birds.y = view.screenHeight / 2f
        birdspeedX=(birdspeed*Math.sin(angle)).toFloat()
        birdspeedY=(-birdspeed*Math.cos(angle)).toFloat()
        birdonscreen = true
    }

    fun update(interval: Double) { //la physique a mettre ici
        if (bird) {
            canonball.x += (interval * canonballVitesseX).toFloat()
            canonball.y += (interval * canonballVitesseY).toFloat()

            /* Vérifions si la balle touche l'obstacle ou pas */
            if (canonball.x + canonballRadius > obstacle.obstacle.left &&
                canonball.y + canonballRadius > obstacle.obstacle.top &&
                canonball.y - canonballRadius < obstacle.obstacle.bottom) {
                canonballVitesseX *= -1
                canonball.offset((5*canonballVitesseX*interval).toFloat(), 0f)
                view.reduceTimeLeft()
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
    }

    fun resetCanonBall() {
        canonballOnScreen = false
    }

    fun draw(canvas: Canvas) { //texture ou hitbox
        canvas.drawCircle(canonball.x, canonball.y, canonballRadius,
            canonballPaint)
    }
}