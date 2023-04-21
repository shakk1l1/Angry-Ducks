package com.example.angryducks

import com.example.angryducks.Bird

class Slingshot {

    fun shot(bird: Bird, x: Float, y: Float){
        var vitx = 0.0
        var vity = 0.0
        val k: Double = 5.0
        vitx = k * x
        vity = k * y

        bird.changespeed(bird, vitx, vity)
    }
}