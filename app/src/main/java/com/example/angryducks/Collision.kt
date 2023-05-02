package com.example.angryducks

import kotlin.math.pow

class collision (){

    companion object {
        fun birdcollisioner(birds: Array<Bird>, pigs: Array<Pig>, interval: Double){
                for (bird in birds) {
                    for (pig in pigs) {
                        if (bird.collidingObjectCountDown == 0) {
                            for (bird2 in birds) {
                                if (bird2.collidingObjectCountDown == 0) {        // collision entre oiseaux
                                    if (bird != bird2) {
                                        if (bird.coo.x != 0f) {
                                            bird.CollisionSpherebird(
                                                bird.coo.x.toDouble(),
                                                bird.coo.y.toDouble(),
                                                bird.birdradius.toDouble(),
                                                bird2.coo.x.toDouble(),
                                                bird2.coo.y.toDouble(),
                                                bird2.birdradius.toDouble(),
                                            )
                                        }
                                        if (bird.collidingbird) {              //bird collide bird
                                            bird.BirdCollideBird2(
                                                bird.vitessex,
                                                bird.vitessey,
                                                bird.mass.toDouble(),
                                                bird2.vitessex,
                                                bird2.vitessey,
                                                bird2.mass.toDouble(),
                                                1.0,
                                                bird2
                                            )
                                        }
                                    }
                                }
                            }
                            if (bird.coo.x != 0f) {
                                if (pig.collidingObjectCountDown == 0) {
                                    bird.CollisionSphereSphere(
                                        bird.coo.x.toDouble(),
                                        bird.coo.y.toDouble(),
                                        bird.birdradius.toDouble(),
                                        pig.coo.x.toDouble(),
                                        pig.coo.y.toDouble(),
                                        pig.radius.toDouble(),
                                    )

                                }
                            }

                            if (bird.colliding) {                 //bird collide pig
                                bird.BirdCollideBird(
                                    bird.vitessex,
                                    bird.vitessey,
                                    bird.mass.toDouble(),
                                    pig.vitessex,
                                    pig.vitessey,
                                    pig.mass.toDouble(),
                                    1.0,
                                    pig
                                )
                            }
                        }
                        if (bird.collidingGroundCountDown == 0) {     // bird colliding ground
                            if (bird.touchinggrass()) {
                                bird.Collideground()
                            }
                        }

                        if (bird.status_launched) {
                            bird.update2(interval)
                        }
                    }

                }
            for(pig in pigs) {
                if (pig.collidingGroundCountDown == 0) {       // pig colliding ground
                    if (pig.touchinggrass()) {
                        pig.Collideground()
                    }
                }

                pig.update2(interval)
            }
        }

        val groundheight = 100f
        val absorbtion = 0.5f
        val m = 0f
        val p = 1000f
        val nx = 0.0
        val ny = 1.0
        val coefRoulement = 0.02
    }


}
