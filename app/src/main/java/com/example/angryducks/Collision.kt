package com.example.angryducks


class Collision{
    companion object {
        fun birdcollisioner(        //Entrées: la liste des oiseaux, la liste des cochons, la liste des objets, l'intervalle d'intégration, la liste des blocs
            birds: Array<Bird>,     //Sorties: None
            pigs: Array<Pig>,               //Vérifie pour l'ensemble des objets du niveau, si ceux-ci entrent en collision, en appellant les méthodes nécessaires
            objets: Array<Objet>,           //et déclenche le calcul de la collision le cas-échéant
            interval: Double,               //Vérifie ensuite pour l'ensemble des objets si ceux-ci rentrent en collision avec les blocs statiques ou avec le sol,
            blocs: Array<ObstacleRectangle> //et appelle les méthodes de collision
        ) {                                 //Ensuite la méthode actualise la position des objets en intégrant leur vitesse sur l'intervalle de temps.
            for (objet in objets) {
                //for (pig in pigs) {
                if (objet.getonscreen()) {
                    if (!objet.getonscreen()) {
                        objet.colliding = false
                    }
                    if (objet.collidingObjectCountDown == 0) {
                        for (objet2 in objets) {
                            if (objet2.collidingObjectCountDown == 0) {        // collision entre les objets
                                if (objet != objet2) {
                                    if (objet.coo.x != 120f && objet.coo.y != 100 - groundheight - 120f) {      //Vérification de la condition de collision
                                        objet.collisionSphereSphere(
                                            objet.coo.x.toDouble(),
                                            objet.coo.y.toDouble(),
                                            objet.getradius().toDouble(),
                                            objet2.coo.x.toDouble(),
                                            objet2.coo.y.toDouble(),
                                            objet2.getradius().toDouble(),
                                        )
                                    }
                                    if (objet.colliding) {              //activation de la collision
                                        objet.sphereCollideSphere(
                                            objet.vitessex,
                                            objet.vitessey,
                                            objet.mass,
                                            objet2.vitessex,
                                            objet2.vitessey,
                                            objet2.mass,
                                            0.7,
                                            1.0,
                                            objet2,
                                            objet.coo.x.toDouble(),
                                            objet.coo.y.toDouble(),
                                            objet2.coo.x.toDouble(),
                                            objet2.coo.y.toDouble(),
                                        )
                                        if (objet2 is Pig) {        //Appel de la méthode de détection de dégat si l'objet qui collisionne est un cochon
                                            objet2.deteriorationdetect(
                                                objet2.vitessex,
                                                objet2.vitessey,
                                                objet2.mass,
                                                objet2.hpinit
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (objet.collidingGroundCountDown == 0) {     // Vérification de la collision entre l'objet et le sol
                        if (objet.touchinggrass()) {
                            objet.collideground()
                        }
                    }
                    for (bloc in blocs) {
                        if (!bloc.getkilled()) {
                            for (point in bloc.pooints) {
                                if (objet.collidingpointCountDown == 0) {     // Vérification de la collision entre l'objet et les différents blocs
                                    if (objet.touchingobstaclepoint(          //La "hit-box" est composée des 4 coins et des 4 segments qui les relient
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
                            for (segment in bloc.obstaacles) {
                                if (objet.collidingGroundCountDown == 0) {
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
            }
            for (bird in birds) {
                if (bird.statuslaunched && bird.getonscreen()) {        //actualisation de la position des oiseaux
                    bird.update2(interval)
                }
            }
            for (pig in pigs) {                                         //actualisation de la position des cochons
                if(pig.getonscreen()) {
                    pig.update2(interval)
                }
            }

        }
        const val groundheight = 100f   //hauteur du sol
        const val absorbtion = 0.6f     //facteur d'absorbtion lors des collisions entre un objet et un bloc ou le sol
        const val m = 0.0               //pente du sol
        const val nx = 0.0              //Vecteur normal à la surface du sol
        const val ny = 1.0
        const val coefRoulement = 0.005 //coefficient de frottement de roulement
    }
}



