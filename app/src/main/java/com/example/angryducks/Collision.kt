package com.example.angryducks


class Collision{

    companion object {
        fun birdcollisioner(birds: Array<Bird>, pigs: Array<Pig>, interval: Double){
                for (bird in birds) {
                    for (pig in pigs) {
                        if(pig.onscreen==false){
                            pig.collidingpig=false
                        }
                        if (bird.collidingObjectCountDown == 0) {
                            for (bird2 in birds) {
                                if (bird2.collidingObjectCountDown == 0) {        // collision entre oiseaux
                                    if (bird != bird2) {
                                        if (bird.coo.x != 0f) {
                                            bird.collisionSpherebird(
                                                bird.coo.x.toDouble(),
                                                bird.coo.y.toDouble(),
                                                bird.birdradius.toDouble(),
                                                bird2.coo.x.toDouble(),
                                                bird2.coo.y.toDouble(),
                                                bird2.birdradius.toDouble(),
                                            )
                                        }
                                        if (bird.collidingbird) {              //bird collide bird
                                            bird.birdCollideBird2(
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
                                    bird.collisionSpherePig(
                                        bird.coo.x.toDouble(),
                                        bird.coo.y.toDouble(),
                                        bird.birdradius.toDouble(),
                                        pig.coo.x.toDouble(),
                                        pig.coo.y.toDouble(),
                                        pig.radius.toDouble(),
                                    )

                                }
                            }


                            if (bird.collidingpig) {                 //bird collide pig
                                bird.sphereCollidePig(
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
                                bird.collideground()
                            }
                        }
                    }
                    if (bird.statuslaunched) {
                        bird.update2(interval)
                    }
                }
            for(pig in pigs) {
                for (pig2 in pigs){
                    if (pig2 != pig && pig2.onscreen && pig.onscreen){
                        pig.collisionSpherePig(
                            pig.coo.x.toDouble(),
                            pig.coo.y.toDouble(),
                            pig.pigradius.toDouble(),
                            pig2.coo.x.toDouble(),
                            pig2.coo.y.toDouble(),
                            pig2.radius.toDouble(),
                        )
                    }
                    if (pig.collidingpig) {              //bird collide bird
                        pig.sphereCollidePig(
                            pig.vitessex,
                            pig.vitessey,
                            pig.mass.toDouble(),
                            pig2.vitessex,
                            pig2.vitessey,
                            pig2.mass.toDouble(),
                            1.0,
                            pig2
                        )
                    }
                }
                if (pig.collidingGroundCountDown == 0) {       // pig colliding ground
                    if (pig.touchinggrass()) {
                        pig.collideground()
                    }
                }

                pig.update2(interval)
            }
        }

        const val groundheight = 100f
        const val absorbtion = 0.5f
        const val m = 0.05
        //const val p = 1000f+
        const val nx = 0.0499376169
        const val ny = 0.9987523389
        const val coefRoulement = 0.01
    }


}
