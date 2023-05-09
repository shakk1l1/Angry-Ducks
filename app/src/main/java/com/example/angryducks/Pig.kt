package com.example.angryducks

import android.graphics.BitmapFactory
import android.graphics.Canvas
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
    val vxp: Double,
    val vyp: Double,
    private val pigradius: Float,
    override var hp: Int,
    override var killed: Boolean
)

    : Objet(massep, vxp, vyp, view, pigradius), Killable, Pigobserver, OnSizeChanged{

    //----------------------------------------------------------------------------------------------
    // Variables init
    //----------------------------------------------------------------------------------------------
    private var imagepig = BitmapFactory.decodeResource(view.resources,R.drawable.pig1)
    var hpinit=0
    //----------------------------------------------------------------------------------------------
    // Function
    //----------------------------------------------------------------------------------------------
    init {
        coo.x=xp
        coo.y = yp
        vitessex=vxp
        vitessey=vyp
        hpinit = hp
        killed = false
        onscreen=true
        imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
    }
    override fun reset() {   //Entrées:None, Sorties:None
        coo.x = xp           //Replace les cochons à leur position initiale lorque le niveau est réinitialisé et réinitialise leurs variables
        coo.y = yp
        onscreen = true
        killed = false
        vitessex = vxp
        vitessey = vyp
        hp = hpinit
        imagepig = BitmapFactory.decodeResource(view.resources,R.drawable.pig1)
        imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
    }

    override fun set_posy(newy: Float){
        yp += newy
        coo.y = yp
    }

    fun draw(canvas: Canvas) {              //Entrées:canva, Sorties:None
        if(onscreen) {                      //Affiche le cochon à l'écran, si il est dans la zone du niveau
            imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
            canvas.drawBitmap(imagepig,coo.x-pigradius,coo.y-pigradius,null)
        }
    }
    override fun update2(interval: Double) {    //Entrées:intervalle , Sorties:None
        super.update2(interval)                 //Actualise le cochon en appelant le code update2 de la classe objet et s'occupe de tuer le cochon si il a prit trop de dégats
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
    override fun changeaftercoll(v2x:Double, v2y:Double) {    //Entrées:changement de vitesse en x et en y, Sorties:None
        vitessex-=v2x                                         //Modifie la vitesse du cochon après une collision et active la détection des dégats
        vitessey-=v2y
        deteriorationdetect(vitessex, vitessey,massep, hpinit)
        collidingObjectCountDown=10
    }

    override fun collideground(){
        super.collideground()
        deteriorationdetect(vitessex, vitessey, mass, hpinit)
    }

    override fun low() {                                                            //Entrées:, Sorties:None
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
            else {
                imagepig = imagepig.scale((2*pigradius).toInt(),(2*pigradius).toInt())
                imagepig = BitmapFactory.decodeResource(view.resources,R.drawable.pig1)
            }
        }

    }
}
