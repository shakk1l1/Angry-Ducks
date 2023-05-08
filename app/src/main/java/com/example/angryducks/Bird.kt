package com.example.angryducks

import android.graphics.BitmapFactory
import android.graphics.Canvas
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

    private var imagebird = BitmapFactory.decodeResource(view.resources,R.drawable.duck)
    var statuslaunched = false

    //----------------------------------------------------------------------------------------------
    // Functions
    //----------------------------------------------------------------------------------------------
    init {
        imagebird = imagebird.scale((2*birdradius).toInt(),(2*birdradius).toInt())

    }
    override fun reset(){
        onscreen = false
        coo.x = 0f
        coo.y = 0f
    }
    fun draw(canvas: Canvas) {
        if (onscreen) {
            canvas.drawBitmap(imagebird,coo.x-birdradius,coo.y-birdradius,null)

        }
    }

    fun launch(diffx: Double, diffy: Double){
        coo.x = (view.startpositionx)
        coo.y = (view.screenHeight - groundheight - view.startpositiony)
        vitessex= (-(2*diffx))
        vitessey= (-(2*diffy))
        onscreen = true
        statuslaunched = true

    }
}