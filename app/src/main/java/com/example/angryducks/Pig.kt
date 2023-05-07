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


class Pig(
    view: LevelView,
    private val massep: Double,
    private var xp: Float,
    private var yp: Float,
    vxp: Double,
    vyp: Double,
    private val pigradius: Float,
    override var hp: Int,
    override var killed: Boolean
)

    : Objet(massep, vxp, vyp, view, pigradius), Killable, Pigobserver{
    //----------------------------------------------------------------------------------------------
    // Variables init
    //----------------------------------------------------------------------------------------------
    private var imagepig = BitmapFactory.decodeResource(view.resources,R.drawable.pig1)

    //----------------------------------------------------------------------------------------------
    // Function
    //----------------------------------------------------------------------------------------------
    init {
        coo.x=xp
        coo.y = yp
        vitessex=vxp
        vitessey=vyp
        killed = false
        onscreen=true
        imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
    }
    override fun reset() {
        coo.x = xp
        coo.y = yp
        onscreen = true
        vitessex = 0.0
        vitessey = 0.0
        hp = 200
        imagepig = BitmapFactory.decodeResource(view.resources,R.drawable.pig1)
        imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
        killed = false
    }

    fun draw(canvas: Canvas) {
        if(onscreen) {
            imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
            canvas.drawBitmap(imagepig,coo.x-pigradius,coo.y-pigradius,null)
        }
    }
    override fun update2(interval: Double) {
        super.update2(interval)
        if(killed && onscreen){
            onscreen = false
            view.pigleft -= 1
            view.mediaPigdead.start()
        }
        if(!onscreen && !killed){
            killed = true
            view.pigleft -= 1
            view.mediaPigdead.start()
        }
    }
    override fun changeaftercoll(v2x:Double, v2y:Double) {
        vitessex-=v2x
        vitessey-=v2y
        deteriorationdetect(vitessex, vitessey,massep)
        collidingObjectCountDown=10
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
