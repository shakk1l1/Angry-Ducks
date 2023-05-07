package com.example.angryducks


class Collision{
    companion object {
        fun birdcollisioner(
            birds: Array<Bird>,
            pigs: Array<Pig>,
            objets: Array<Objet>,
            interval: Double,
            blocs: Array<ObstacleRectangle>
        ) {
            for (objet in objets) {
                //for (pig in pigs) {
                if (!objet.getonscreen()) {
                    objet.colliding = false
                }
                if (objet.collidingObjectCountDown == 0) {
                    for (objet2 in objets) {
                        if (objet2.collidingObjectCountDown == 0) {        // collision entre oiseaux
                            if (objet != objet2) {
                                if (objet.coo.x != 0f /*&& objet.coo.y != view.screenHeight - groundheight - 120f*/) {
                                    objet.collisionSphereSphere(
                                        objet.coo.x.toDouble(),
                                        objet.coo.y.toDouble(),
                                        objet.getradius().toDouble(),
                                        objet2.coo.x.toDouble(),
                                        objet2.coo.y.toDouble(),
                                        objet2.getradius().toDouble(),
                                    )
                                }
                                if (objet.colliding) {              //bird collide bird
                                    objet.sphereCollideSphere(
                                        objet.vitessex,
                                        objet.vitessey,
                                        objet.mass,
                                        objet2.vitessex,
                                        objet2.vitessey,
                                        objet2.mass,
                                        0.7,
                                        objet2
                                    )
                                    if(objet2 is Pig){
                                        objet2.deteriorationdetect(objet2.vitessex, objet2.vitessey, objet2.mass)
                                    }
                                }
                            }
                        }
                    }
                }
                if (objet.collidingGroundCountDown == 0) {     // bird colliding ground
                    if (objet.touchinggrass()) {
                        objet.collideground()
                    }
                }
                for (bloc in blocs) {
                    if (!bloc.getkilled()) {
                        for (point in bloc.pooints) {
                            if (objet.collidingpointCountDown == 0) {
                                if (objet.touchingobstaclepoint(
                                        point.positionx,
                                        point.positiony,
                                        point.rayon
                                    )
                                ) {
                                    objet.collideobstaclepoint(
                                        point.positionx,
                                        point.positiony,
                                        bloc
                                    )
                                }
                            }
                        }
                    }
                    if (objet.collidingGroundCountDown == 0) {
                        if (!bloc.getkilled()) {
                            for (segment in bloc.obstaacles) {
                                if (objet.touchingobstaclesegment(
                                        segment.postionx,
                                        segment.postiony,
                                        segment.longueur,
                                        segment.nx,
                                        segment.ny
                                    )
                                ) {
                                    objet.collideobstaclesegment(
                                        segment.nx,
                                        segment.ny,
                                        bloc
                                    )
                                }
                            }
                        }
                    }
                }

            }
            for (bird in birds) {
                if (bird.statuslaunched && bird.getonscreen()) {
                    bird.update2(interval)
                }
            }
            for (pig in pigs) {
                if(pig.getonscreen()) {
                    pig.update2(interval)
                }
            }

        }
        const val groundheight = 100f
        const val absorbtion = 0.6f
        const val m = 0.0
        const val nx = 0.0
        const val ny = 1.0
        const val coefRoulement = 0.005
    }
}



