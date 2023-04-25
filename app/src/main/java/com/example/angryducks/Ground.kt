package com.example.angryducks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import kotlin.math.pow

class Ground (var height : Float, val angle: Float, var screenHeight: Float, var screenWidth: Float, val view: LevelView){

    val groundtexture = Paint()
    val ground = RectF(0f, view.screenHeight-height, view.screenWidth-0f, view.screenHeight-0f)

    init {
        groundtexture.color = Color.parseColor("#4caf50")
    }
    fun draw(canvas: Canvas) {
        canvas.drawRect(ground, groundtexture)
    }

    fun setRect() {
        ground.set(0f, screenHeight - height,
            screenWidth, screenHeight)
    }
    //je les mets là mais à discuter
    fun CollisionSpherePlan(objet: Objet,x: Double,y: Double,r: Double,m:Double,p:Double) {
        var distancecarre:Double=0.0
        distancecarre=(m*x+p-y).pow(2)/(1+m.pow(2))
        var colliding=(distancecarre<r.pow(2))
        if (colliding){
            CollideGround(objet,objet.vitessex,objet.vitessey,1.0,1.0, collision.absorbtion.toDouble())
        }
    }
    fun CollideGround(objet: Objet,vx:Double,vy:Double,nx:Double,ny:Double,coef:Double) {
        var dvx : Double = vx * nx * (1+coef)
        var dvy : Double = vy * ny * (1+coef)
        objet.vitessex = vx - dvx
        objet.vitessey = vy - dvy
    }
    fun Draw(){

    }
    fun Reset(){

    }
    fun istouching(){

    }
}
