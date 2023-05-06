package com.example.angryducks


class Collision{

    /*companion object {
        fun birdcollisioner(birds: Array<Bird>, pigs: Array<Pig>, interval: Double, blocs: Array<ObstacleRectangle>){
                for (bird in birds) {
                    for (pig in pigs) {
                        if(!pig.getonscreen()){
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
                                        pig.getradius().toDouble(),
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
                        for(bloc in blocs) {

                            for (point in bloc.pooints) {
                                if (bird.collidingpointCountDown==0) {
                                    if (bird.touchingobstaclepoint(
                                            point.positionx,
                                            point.positiony,
                                            point.rayon
                                        )
                                    ) {
                                        //bloc.deteriorationdetect(bird.vitessex, bird.vitessey, bird.mass)
                                        bird.collideobstaclepoint(point.positionx, point.positiony, bloc)
                                    }
                                }
                            }
                            if (bird.collidingGroundCountDown == 0) {
                                if (!bloc.getkilled()) {
                                    for (segment in bloc.obstaacles) {
                                        if (bird.touchingobstaclesegment(
                                                segment.postionx,
                                                segment.postiony,
                                                segment.longueur,
                                                segment.nx,
                                                segment.ny
                                            )
                                        ) {
                                            //bloc.deteriorationdetect(bird.vitessex, bird.vitessey, bird.mass)
                                            bird.collideobstaclesegment(
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
                    if (bird.statuslaunched) {
                        bird.update2(interval)
                    }
                }
            for(pig in pigs) {
                for (pig2 in pigs){
                    if (pig2 != pig && pig2.getonscreen() && pig.getonscreen()){
                        pig.collisionSpherePig(
                            pig.coo.x.toDouble(),
                            pig.coo.y.toDouble(),
                            pig.pigradius.toDouble(),
                            pig2.coo.x.toDouble(),
                            pig2.coo.y.toDouble(),
                            pig2.getradius().toDouble(),
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
                for(bloc in blocs) {
                    if (pig.collidingGroundCountDown == 0) {
                        if (!bloc.getkilled()) {
                            for (segment in bloc.obstaacles) {
                                if (pig.touchingobstaclesegment(
                                        segment.postionx,
                                        segment.postiony,
                                        segment.longueur,
                                        segment.nx,
                                        segment.ny
                                    )
                                ) {
                                    //bloc.deteriorationdetect(bird.vitessex, bird.vitessey, bird.mass)
                                    pig.collideobstaclesegment(
                                        segment.nx,
                                        segment.ny,
                                        bloc
                                    )
                                }
                            }
                        }
                    }

                    for (point in bloc.pooints) {
                        if (pig.collidingpointCountDown==0) {
                            if (pig.touchingobstaclepoint(
                                    point.positionx,
                                    point.positiony,
                                    point.rayon
                                )
                            ) {
                                //bloc.deteriorationdetect(bird.vitessex, bird.vitessey, bird.mass)
                                pig.collideobstaclepoint(point.positionx, point.positiony, bloc)
                            }
                        }
                    }
                }

                pig.update2(interval)
            }
        }*/
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
                                        objet.mass.toDouble(),
                                        objet2.vitessex,
                                        objet2.vitessey,
                                        objet2.mass.toDouble(),
                                        1.0,
                                        objet2
                                    )
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
                                    //bloc.deteriorationdetect(bird.vitessex, bird.vitessey, bird.mass)
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
                if (bird.statuslaunched) {
                    bird.update2(interval)
                }
            }
            for (pig in pigs) {
                pig.update2(interval)
            }

        }
        const val groundheight = 100f
        const val absorbtion = 0.6f
        const val m = 0.0
        //const val p = 1000f+
        const val nx = 0.0
        const val ny = 1.0
        const val coefRoulement = 0.005
    }
}



