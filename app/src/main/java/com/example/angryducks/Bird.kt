package com.example.angryducks

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.graphics.scale

class Bird (view: LevelView,
            private var groundheight: Float,
            private val birdradius:Float,
            masseb:Double
            )
    : Objet(masseb, 0.0, 0.0, view, birdradius){

    //----------------------------------------------------------------------------------------------
    // Variables init
    //----------------------------------------------------------------------------------------------

    private val birdtexture = Paint()
    private var imagebird = BitmapFactory.decodeResource(view.resources,R.drawable.duck)
    var statuslaunched = false

    //----------------------------------------------------------------------------------------------
    // Functions
    //----------------------------------------------------------------------------------------------
    init {
        birdtexture.color = Color.RED
        imagebird = imagebird.scale((2*birdradius).toInt(),(2*birdradius).toInt())

    }
    override fun reset(){
        onscreen = false
        birdtexture.color = Color.RED
        coo.x = 0f
        coo.y = 0f
    }
    fun draw(canvas: Canvas) {
        if (onscreen) {
            canvas.drawBitmap(imagebird,coo.x-birdradius,coo.y-birdradius,null)

        }
    }

    fun launch(diffx: Double, diffy: Double){
        coo.x = (birdradius)
        coo.y = (view.screenHeight - groundheight - 120f)
        vitessex= (-(3*diffx))
        vitessey= (-(3*diffy))
        onscreen = true
        statuslaunched = true

    }
}