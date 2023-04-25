package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.angryducks.LevelView
import com.example.angryducks.collision.Companion.absorbtion
import kotlin.math.pow

class Obstacle (var obstacleDistance: Float, var obstacleDebut: Float, var obstacleFin: Float, var initialObstacleVitesse: Float, var width: Float, var view: LevelView)
{
    val obstacle = RectF(obstacleDistance, obstacleDebut,
        obstacleDistance + width, obstacleFin)
    val obstaclePaint = Paint()
    var obstacleVitesse= initialObstacleVitesse

    fun setRect() {
        obstacle.set(obstacleDistance, obstacleDebut,
            obstacleDistance + width, obstacleFin)
        obstacleVitesse= initialObstacleVitesse
    }


    fun draw(canvas: Canvas) {
        obstaclePaint.color = Color.RED
        canvas.drawRect(obstacle, obstaclePaint)
    }

    fun update(interval: Double) {
        var up = (interval * obstacleVitesse).toFloat()
        obstacle.offset(0f, up)
        if (obstacle.top < 0 || obstacle.bottom > view.screenHeight) {
            obstacleVitesse *= -1
            up = (interval * 3 * obstacleVitesse).toFloat()
            obstacle.offset(0f, up)
        }
    }
    fun resetObstacle() {
        obstacleVitesse = initialObstacleVitesse
        obstacle.set(obstacleDistance, obstacleDebut,
            obstacleDistance + width, obstacleFin)
    }
    //à implémenter encore
    fun CollisionSpherePlan(objet: Objet,x: Double,y: Double,r: Double,m:Double,p:Double) {
        var distancecarre:Double=0.0
        distancecarre=(m*x+p-y).pow(2)/(1+m.pow(2))
        var colliding=(distancecarre<r.pow(2))
        if (colliding){
            CollideGround(objet,objet.vitessex,objet.vitessey,1.0,1.0,absorbtion.toDouble())
        }
    }
    fun CollideGround(objet: Objet,vx:Double,vy:Double,nx:Double,ny:Double,coef:Double) {
        var dvx : Double = vx * nx * (1+coef)
        var dvy : Double = vy * ny * (1+coef)
        objet.vitessex = vx - dvx
        objet.vitessey = vy - dvy
    }
}