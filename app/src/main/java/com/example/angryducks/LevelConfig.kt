package com.example.angryducks

data class LevelConfig(
    val birds: List<Bird>,
    val pigs: List<Pig>,
    val blocks: List<ObstacleRectangle>
)