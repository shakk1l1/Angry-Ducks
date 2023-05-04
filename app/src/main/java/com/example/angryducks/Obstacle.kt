package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.angryducks.Collision.Companion.absorbtion
import kotlin.math.pow

class Obstacle (var obstacleDistance: Float, var obstacleDebut: Float, var obstacleFin: Float, var initialObstacleVitesse: Float, var width: Float, view: LevelView,
                override var hp: Int,
                override var killed: Boolean
) : Objet(20f, 0.0, 0.0, 0.0 ,0.0, view), Killable
{
    private val obstacle = RectF(obstacleDistance, obstacleDebut,
        obstacleDistance + width, obstacleFin)
    private val obstaclePaint = Paint()
    private var obstacleVitesse= initialObstacleVitesse

    private fun setRect() {
        obstacle.set(obstacleDistance, obstacleDebut,
            obstacleDistance + width, obstacleFin)
        obstacleVitesse= initialObstacleVitesse
    }


    fun draw(canvas: Canvas) {
        obstaclePaint.color = Color.RED
        canvas.drawRect(obstacle, obstaclePaint)
    }

    private fun update(interval: Double) {
        var up = (interval * obstacleVitesse).toFloat()
        obstacle.offset(0f, up)
        if (obstacle.top < 0 || obstacle.bottom > view.screenHeight) {
            obstacleVitesse *= -1
            up = (interval * 3 * obstacleVitesse).toFloat()
            obstacle.offset(0f, up)
        }
    }
    private fun resetObstacle() {
        obstacleVitesse = initialObstacleVitesse
        obstacle.set(obstacleDistance, obstacleDebut,
            obstacleDistance + width, obstacleFin)
    }
    //à implémenter encore

    private fun collisionSpherePlan(objet: Objet,x: Double,y: Double,r: Double,m:Double,p:Double) {
        val distancecarre = (m*x+p-y).pow(2)/(1+m.pow(2))
        val colliding=(distancecarre<r.pow(2))
        if (colliding){
            collideGround(objet,objet.vitessex,objet.vitessey,1.0,1.0,absorbtion.toDouble())
        }
    }
    private fun collideGround(objet: Objet,vx:Double,vy:Double,nx:Double,ny:Double,coef:Double) {
        val dvx : Double = vx * nx * (1+coef)
        val dvy : Double = vy * ny * (1+coef)
        objet.vitessex = vx - dvx
        objet.vitessey = vy - dvy
    }

    override fun low() {
        TODO("Not yet implemented")
    }

    override fun mid() {
        TODO("Not yet implemented")
    }

    override fun touchinggrass(): Boolean {
        TODO("Not yet implemented")
    }

    override fun collideground() {
        TODO("Not yet implemented")
    }

    override fun touchingobstaclesegment(
        postionx: Double,
        postiony: Double,
        longueur: Double,
        nx: Double,
        ny: Double
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun collideobstaclesegment(nx: Double, ny: Double) {
        TODO("Not yet implemented")
    }
}