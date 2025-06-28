package com.example.angryducks

import android.view.View
import com.example.angryducks.LevelView


object LevelData {

    fun getLevel(view: LevelView, level: Int): LevelConfig {
        return when (level) {
            1 -> level1(view)
            2 -> level2(view)
            3 -> level3(view)
            else -> level1(view)
        }
    }


    private fun level1(view: LevelView): LevelConfig {
        return LevelConfig(
            birds = listOf(
                Bird(view, Collision.groundheight, 30f, 10.0),
                Bird(view, Collision.groundheight, 20f, 20.0),
                Bird(view, Collision.groundheight, 50f, 30.0),
                Bird(view, Collision.groundheight, 30f, 50.0),
                Bird(view, Collision.groundheight, 30f, 20.0),
                Bird(view, Collision.groundheight, 30f, 20.0),
                Bird(view, Collision.groundheight, 30f, 20.0),
                Bird(view, Collision.groundheight, 30f, 20.0),
                Bird(view, Collision.groundheight, 30f, 20.0),
                Bird(view, Collision.groundheight, 90f, 50.0)
            ),
            pigs = listOf(
                Pig(view, 20.0, 1350f, 380f, 0.0, 0.0, 20f, 100, false),
                Pig(view, 500.0, 1800f, 500f, 0.0, 0.0, 90f, 250, false),
                Pig(view, 20.0, 1140f, 765f, 0.0, 0.0, 30f, 60, false),
                Pig(view, 20.0, 1330f, 760f, 0.0, 0.0, 30f, 60, false),
                Pig(view, 100.0, 710f, 540f, 0.0, 0.0, 40f, 100, false)
            ),
            blocks = listOf(
                ObstacleRectangle(10.0, -90.0, 0.0, 10.0, 10.0, 25, false),
                ObstacleRectangle(1300.0, 900.0, 0.0, 100.0, 1900.0, 2500, false),
                ObstacleRectangle(330.0, 896.0, 0.6, 50.0, 100.0, 2500, false),
                ObstacleRectangle(650.0, 650.0, 0.0, 400.0, 30.0, 250, false),
                ObstacleRectangle(1275.0, 600.0, 0.0, 30.0, 750.0, 100, false),
                ObstacleRectangle(900.0, 650.0, 0.0, 400.0, 30.0, 250, false),
                ObstacleRectangle(775.0, 600.0, 0.0, 30.0, 250.0, 250, false),
                ObstacleRectangle(1650.0, 650.0, 0.0, 400.0, 30.0, 250, false),
                ObstacleRectangle(1900.0, 650.0, 0.0, 400.0, 30.0, 250, false),
                ObstacleRectangle(1775.0, 600.0, 0.0, 30.0, 250.0, 100, false),
                ObstacleRectangle(685.0, 370.0, 1.1, 35.0, 400.0, 150, false),
                ObstacleRectangle(865.0, 370.0, -1.1, 35.0, 400.0, 150, false),
                ObstacleRectangle(1685.0, 370.0, 1.1, 35.0, 400.0, 150, false),
                ObstacleRectangle(1865.0, 370.0, -1.1, 35.0, 400.0, 150, false),
                ObstacleRectangle(775.0, 150.0, 0.0, 100.0, 10.0, 10, false),
                ObstacleRectangle(1775.0, 150.0, 0.0, 100.0, 10.0, 10, false),
                ObstacleRectangle(1100.0, 525.0, 0.0, 150.0, 30.0, 250, false),
                ObstacleRectangle(1450.0, 525.0, 0.0, 150.0, 30.0, 250, false),
                ObstacleRectangle(1175.0, 300.0, 1.0, 25.0, 400.0, 150, false),
                ObstacleRectangle(1375.0, 300.0, -1.0, 25.0, 400.0, 150, false),
                ObstacleRectangle(1275.0, 100.0, 0.0, 100.0, 10.0, 10, false),
                ObstacleRectangle(1275.0, 400.0, 0.0, 10.0, 350.0, 100, false),
                ObstacleRectangle(1450.0, 800.0, 0.0, 100.0, 10.0, 10, false),
                ObstacleRectangle(1100.0, 800.0, 0.0, 100.0, 10.0, 10, false),
                ObstacleRectangle(1150.0, 825.0, 0.0, 50.0, 10.0, 10, false),
                ObstacleRectangle(1400.0, 825.0, 0.0, 50.0, 10.0, 10, false),
                ObstacleRectangle(1425.0, 800.0, 0.0, 10.0, 50.0, 10, false),
                ObstacleRectangle(1125.0, 800.0, 0.0, 10.0, 50.0, 10, false),
                ObstacleRectangle(1275.0, 825.0, 0.0, 50.0, 175.0, 100, false)
            )
        )
    }



    private fun level2(view: LevelView): LevelConfig {
        return LevelConfig(
            birds = listOf(
                Bird(view, Collision.groundheight, 30f, 5.0),
                Bird(view, Collision.groundheight, 20f, 20.0),
                Bird(view, Collision.groundheight, 50f, 30.0),
                Bird(view, Collision.groundheight, 30f, 50.0),
                Bird(view, Collision.groundheight, 30f, 20.0),
                Bird(view, Collision.groundheight, 30f, 20.0),
                Bird(view, Collision.groundheight, 30f, 20.0)
            ),
            pigs = listOf(
                Pig(view, 20.0, 1500f, 880f, 0.0, 0.0, 20f, 100, false),
                Pig(view, 500.0, 1700f, 810f, 0.0, 0.0, 90f, 250, false),
                Pig(view, 20.0, 1050f, 325f, 0.0, 0.0, 30f, 60, false),
                Pig(view, 20.0, 1150f, 210f, 0.0, 0.0, 30f, 60, false)
            ),
            blocks = listOf(
                ObstacleRectangle(1000.0, 520.0, 1.047, 50.0, 500.0, 250, false),
                ObstacleRectangle(1157.0, 314.0, 0.0, 50.0, 100.0, 250, false),
                ObstacleRectangle(855.0, 760.0, 0.785, 50.0, 100.0, 250, false),
                ObstacleRectangle(785.0, 810.0, 0.523, 50.0, 100.0, 250, false),
                ObstacleRectangle(700.0, 835.0, 0.1, 50.0, 100.0, 250, false),
                ObstacleRectangle(610.0, 829.0, -0.17, 50.0, 100.0, 250, false),
                ObstacleRectangle(530.0, 800.0, -0.5, 50.0, 100.0, 250, false),
                ObstacleRectangle(1285.0, 600.0, -1.3, 20.0, 700.0, 250, false),
                ObstacleRectangle(600.0, 870.0, 1.047, 20.0, 100.0, 250, false)
            )
        )
    }


    private fun level3(view: LevelView): LevelConfig {
        return LevelConfig(
            birds = listOf(

            ),
            pigs = listOf(

            ),
            blocks = listOf(

            )
        )
    }
}
