package com.example.angryducks

import android.graphics.PointF
import kotlin.math.absoluteValue
import kotlin.math.pow

abstract class Objet(
    val mass: Double,
    var vitessex: Double,
    var vitessey: Double,
    val view: LevelView,
    private val radius: Float
){
    //----------------------------------------------------------------------------------------------
    // Variables init
    //----------------------------------------------------------------------------------------------

    protected var onscreen = true
    var coo = PointF()
    var colliding = false
    var collidingGroundCountDown = 0
    var collidingObjectCountDown = 0
    var collidingpointCountDown = 0

    //----------------------------------------------------------------------------------------------
    // Function
    //----------------------------------------------------------------------------------------------

    fun getonscreen(): Boolean {return onscreen}    //Entrées:None , Sorties:retourne si l'objet est présent sur la zone de l'écran
    fun getradius(): Float{return radius}

    open fun reset(){   //Entrées:None , Sorties:None
        coo.x = 0f      //Réinitialise les coordonées de l'objet en cas de reset
        coo.y = 0f

    }
    open fun update2(interval:Double){                  //Entrées:intervalle , Sorties:None
        if(onscreen) {                                  //Actualise la position de l'objet en intégrant sa vitesse sur l'intervalle de temps
            coo.x += (interval * vitessex).toFloat()    //Actualise la varaible onscreen en vérifiant que l'objet se trouve à l'écan
            coo.y += (interval * vitessey).toFloat()    //Décrémente les variables de "collision count down"
                                                        //Accélère les objets vers le bas
            if (coo.x > view.screenWidth + 100f
                || coo.x < -100f
            ) {
                onscreen = false
            }
            else if ( coo.y < - 2000f) {
                onscreen = false
            }

            if (collidingGroundCountDown>0) {
                collidingGroundCountDown-=1
            }
            if (collidingObjectCountDown>0) {
                collidingObjectCountDown-=1
            }
            if (collidingpointCountDown>0) {
                collidingpointCountDown-=1
            }

            vitessey += (interval * 1000.0f).toFloat()
        }
    }

    fun collisionSphereSphere(x1:Double,y1:Double,r1:Double,x2:Double,y2:Double,r2:Double) {    //Entrées:position x, y de l'objet 1 et 2 et leurs rayons , Sorties:None
        val one = ((x1-x2).pow(2)+(y1-y2).pow(2)).pow(0.5)                             //Détecte les collisions entre les objets en calculant leur distance et en la comparant à leur rayon
        val two = r1+r2
        colliding =(one<two)

    }
    fun sphereCollideSphere(v1x:Double,v1y:Double,m1:Double,v2x:Double,v2y:Double,m2:Double,coef:Double, objet: Objet) {
        val vmoyx:Double = (m1*v1x+m2*v2x)/(m1+m2)
        val vmoyy:Double = (m1*v1y+m2*v2y)/(m1+m2)  //Entrées: vitesse x et y des 2 objets ainsi que leurs masses respectives, Sorties:None
        val dv1x=(1.0+coef)*(v1x-vmoyx)             //Calcule les vitesses finale des 2 objets qui se collisionnent, modifie la vitesse de l'objet exécutat la méthode
        val dv1y=(1.0+coef)*(v1y-vmoyy)             //et appelle le deuxième objet pour qu'il change lui même sa vitesse
        val dv2x=(1+coef)*(v2x-vmoyx)
        val dv2y=(1+coef)*(v2y-vmoyy)
        vitessex-=dv1x
        vitessey-=dv1y
        colliding = false
        collidingObjectCountDown=10
        objet.changeaftercoll(dv2x, dv2y)
    }

    open fun changeaftercoll(v2x:Double, v2y:Double) {      //Entrées:différence de vitesse x et y , Sorties:None
        vitessex-=v2x                                       //Change la vitesse de l'objet collisioné avec le résultat calculé dans la fonction juste au dessus
        vitessey-=v2y
        collidingObjectCountDown=10
    }

    fun touchinggrass(): Boolean {
        val distancecarre = ((Collision.m*coo.x-(view.screenHeight-Collision.groundheight)+coo.y).pow(2)/(1+Collision.m.pow(2)))
        return (distancecarre<radius.pow(2))
    }


    open fun collideground(){                    //Entrées:None , Sorties:None
        val prodvect=vitessex * Collision.nx-vitessey*Collision.ny  //Calcule le changement de vitesse que subit un objet après avoir touché le sol
        if (-50.0<prodvect && prodvect<=0.0) {  //Contact permanent // et change la vitesse de l'objet
            val dvx : Double = prodvect * Collision.nx
            val dvy : Double = -prodvect * Collision.ny
            vitessex = (vitessex - dvx)*(1.0-Collision.coefRoulement)
            vitessey = (vitessey - dvy)*(1.0-Collision.coefRoulement)
        }
        else if (prodvect<=-50.0){  //Choc
            val dvx : Double = prodvect * Collision.nx * (1+Collision.absorbtion)
            val dvy : Double = -prodvect * Collision.ny * (1+Collision.absorbtion)
            vitessex = (vitessex - dvx)
            vitessey = (vitessey - dvy)
            collidingGroundCountDown=1
        }
    }

    fun touchingobstaclesegment(positionx:Double,positiony:Double,longueur:Double,nx:Double,ny:Double) : Boolean{

        return if (((positionx - coo.x) * nx + (positiony - coo.y) * ny).absoluteValue < radius){
            ((positionx - coo.x) * ny - (positiony - coo.y) * nx).absoluteValue < ( longueur/2)
        } else{              //Entrées:position en x et y du segment de l'obstacle, sa longueur et son orientation (nx,ny), le vecteur normal
            false            // Sorties:retourne si l'objet concerné touche le sol du niveau
        }
    }
    fun collideobstaclesegment(nx:Double, ny:Double, bloc: ObstacleRectangle){      //Entrées:vecteur normal au segment (nx,ny) , Sorties:None
        val prodvect=vitessex * nx+vitessey*ny                                      //Calcule le changement de vitesse que subit un objet après avoir touché un segment d'un obstacle
        if (-50.0<prodvect && prodvect< 0.0) {//Contact permanent                   //et modifie la vitesse de l'objet concerné
            val dvx : Double = prodvect * nx
            val dvy : Double = prodvect * ny
            vitessex = (vitessex - dvx)*(1.0-Collision.coefRoulement)
            vitessey = (vitessey - dvy)*(1.0-Collision.coefRoulement)

        }
        else if (-50.0>=prodvect){              //Choc
            val dvx: Double = prodvect * nx * (1 + Collision.absorbtion)
            val dvy: Double = prodvect * ny * (1 + Collision.absorbtion)
            bloc.deteriorationdetect(vitessex, vitessey, mass, bloc.hpinit)
            vitessex = (vitessex - dvx)
            vitessey = (vitessey - dvy)
            collidingGroundCountDown = 1
        }
    }

    fun touchingobstaclepoint(positionx:Double,positiony:Double,rayon:Double) :Boolean{             //Entrées:la position du point de collision en x et y et son rayon
        return((coo.x-positionx).pow(2)+(coo.y-positiony).pow(2) < (radius+rayon).pow(2))  //Sortie: Retourne si l'objet rentre en collision avec le point
    }

    fun collideobstaclepoint(positionx:Double,positiony:Double, bloc: ObstacleRectangle){   //Entrées:La position du point de collision et le bloc auquel il appartient
        val distance=((coo.x-positionx).pow(2)+(coo.y-positiony).pow(2)).pow(0.5)  //Sorties: None
        val nx=(coo.x-positionx)/distance                           //Calcule la collision entre l'objet et le point de collision et modifie la vitesse de l'objet après calcul
        val ny=(coo.y-positiony)/distance
        val prodvect=vitessex * nx+vitessey*ny
        if (0.0<=prodvect && prodvect<50.0) {//Contact permanent
            val dvx : Double = prodvect * nx
            val dvy : Double = prodvect * ny
            vitessex = (vitessex - dvx)
            vitessey = (vitessey - dvy)
        }
        else if (50.0>=prodvect) {//Choc
            val dvx : Double = prodvect * nx * (1+Collision.absorbtion)
            val dvy : Double = prodvect * ny * (1+Collision.absorbtion)
            bloc.deteriorationdetect(vitessex, vitessey, mass,bloc.hpinit)
            vitessex = (vitessex - dvx)
            vitessey = (vitessey - dvy)
            collidingpointCountDown = 1
            collidingGroundCountDown = 1
        }
    }
}