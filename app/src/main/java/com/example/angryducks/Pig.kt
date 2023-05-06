package com.example.angryducks

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.graphics.scale
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.pow


class Pig(view: LevelView, val massep : Double, val radius: Float, var xp : Float, var yp : Float,
          var vxp : Double, var vyp : Double, var orp : Float, var vangulp : Float, val pigradius:Float,
          override var hp: Int, override var killed: Boolean)

    : Objet(massep,vxp,vyp,orp.toDouble(),vangulp.toDouble(), view), Killable, Pigobserver{
    private var paintpig = Paint()
    private var death:Boolean = false
    private var imagepig = BitmapFactory.decodeResource(view.resources,R.drawable.pig1)

    val textpaint = Paint()

    init {
        //paintpig.color = Color.parseColor("#056517")
        coo.x=xp
        coo.y = yp
        vitessex=vxp
        vitessey=vyp
        killed = false
        onscreen=true
        textpaint.textSize = 200f
        textpaint.color = Color.BLACK
        imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
    }
    override fun reset() {
        coo.x = xp
        coo.y = yp
        onscreen = true
        vitessex = 0.0
        vitessey = 0.0
        hp = 200
        //paintpig.color = Color.parseColor("#056517")
        imagepig = BitmapFactory.decodeResource(view.resources,R.drawable.pig1)
        imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
        killed = false
        death = false
    }

    fun draw(canvas: Canvas) {
        if(onscreen) {
            /*canvas.drawCircle(
                coo.x, coo.y, radius, paintpig
            )*/
            imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
            canvas.drawBitmap(imagepig,coo.x-pigradius,coo.y-pigradius,null)
            //canvas.drawText("${coo.x} + ${vitessey}+${coo.y} ", 800f, 500f, paintpig)
        }
    }
    override fun update2(interval: Double) {
        super.update2(interval)
        if(killed && onscreen){
            onscreen = false
            view.pigleft -= 1
        }
        if(!onscreen && !death && !killed){
            killed = true
            view.pigleft -= 1
        }
    }
    override fun changeaftercoll(v2x:Double, v2y:Double) {
        vitessex-=v2x
        vitessey-=v2y
        deteriorationdetect(vitessex, vitessey,massep.toDouble())
        collidingObjectCountDown=10
    }

    /*fun CollisionSphereSphere(x1:Double,y1:Double,r1:Double,x2:Double,y2:Double,r2:Double) {
        var one = ((x1-x2).pow(2)+(y1-y2).pow(2)).pow(0.5)
        var two = r1+r2
        collidingpig =(one<two)

    }*/

    /*fun SphereCollidePig(v1x:Double,v1y:Double,m1:Double,v2x:Double,v2y:Double,m2:Double,coef:Double, pig: Pig) {
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
    }*/



    override fun touchinggrass(): Boolean {
        val distancecarre = ((Collision.m*coo.x-(view.screenHeight-Collision.groundheight)+coo.y).pow(2)/(1+Collision.m.pow(2)))
        return (distancecarre<pigradius.pow(2))
    }

    override fun collideground() {
        val prodvect=vitessex * Collision.nx+vitessey*Collision.ny
        if ((prodvect).absoluteValue<50) {
            val dvx : Double = prodvect * Collision.nx
            val dvy : Double = prodvect * Collision.ny
            vitessex = (vitessex - dvx)*(1.0-Collision.coefRoulement)
            vitessey = (vitessey - dvy)*(1.0-Collision.coefRoulement)

        }
        else {
            val dvx : Double = prodvect * Collision.nx * (1+Collision.absorbtion)
            val dvy : Double = prodvect * Collision.ny * (1+Collision.absorbtion)
            vitessex = (vitessex - dvx)
            vitessey = (vitessey - dvy)

            collidingGroundCountDown=3

        }
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

    override fun collideobstaclesegment(nx: Double, ny: Double, bloc: ObstacleRectangle) {
        TODO("Not yet implemented")
    }

    override fun touchingobstaclepoint(positionx: Double, positiony: Double,rayon:Double): Boolean {
        TODO("Not yet implemented")
    }

    override fun collideobstaclepoint(
        positionx: Double,
        positiony: Double,
        bloc: ObstacleRectangle
    ) {
        TODO("Not yet implemented")
    }
    override fun low() {
        imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
        imagepig = BitmapFactory.decodeResource(view.resources,R.drawable.pig_low)
    }

    override fun mid() {
        imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
        imagepig = BitmapFactory.decodeResource(view.resources,R.drawable.pig_mid)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun update(){
        GlobalScope.launch {
            imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
            imagepig = BitmapFactory.decodeResource(view.resources,R.drawable.scared_pig)
            delay(800)
            if(hp<=80){low()}
            else if(hp <= 150){mid()}
            else if(hp > 150){
                imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
                imagepig = BitmapFactory.decodeResource(view.resources,R.drawable.pig1)
            }
        }

    }

}
