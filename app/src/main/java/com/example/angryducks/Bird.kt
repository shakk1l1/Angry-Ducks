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
    override fun reset(){ //Entrées:None, Sorties:None
        onscreen = false  //Replace les oiseaux à leur position initiale lorque le niveau est réinitialisé
        coo.x = 0f
        coo.y = 0f
    }
    fun draw(canvas: Canvas) {                   //Entrées:Canva de l'oiseau, Sorties:None
        if (onscreen) {                          //Affiche l'oiseau à l'écran, si il est dans la zone du niveau
            canvas.drawBitmap(imagebird,coo.x-birdradius,coo.y-birdradius,null)

        }
    }

    fun launch(diffx: Double, diffy: Double){           //Entrées: diffx, diffy (les différences de coordonées de glissament de doigt), Sorties:None
        coo.x = (view.startpositionx)                   //Lance l'oiseau lors d'un glissement de doigt, en le plaçant à la poisition initiale, et en lui donnant sa vitesse
        coo.y = (view.screenHeight - groundheight - view.startpositiony)
        vitessex= (-(2*diffx))
        vitessey= (-(2*diffy))
        onscreen = true
        statuslaunched = true

    }
}