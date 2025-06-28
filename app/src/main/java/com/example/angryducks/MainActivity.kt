package com.example.angryducks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.DragEvent
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.hypot
import kotlin.math.atan


class MainActivity: AppCompatActivity(), GestureDetector.OnGestureListener, View.OnDragListener {

    private var dragStartX = 0f
    private var dragStartY = 0f

    private lateinit var levelView: LevelView
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var gestureDetector: GestureDetector       //Cette classe sert d'intermédiaire avec l'utilisateur lorsque celui-ci est en jeu

    private fun setupLevel1() {
        val config = LevelData.getLevel(levelView, 1)
        levelView.setBirds(config.birds)
        levelView.setPigs(config.pigs)
        levelView.setBlocks(config.blocks)
    }

    private fun setupLevel2() {
        val config = LevelData.getLevel(levelView, 2)
        levelView.setBirds(config.birds)
        levelView.setPigs(config.pigs)
        levelView.setBlocks(config.blocks)
    }

    private fun setupLevel3() {
        val config = LevelData.getLevel(levelView, 3)
        levelView.setBirds(config.birds)
        levelView.setPigs(config.pigs)
        levelView.setBlocks(config.blocks)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        levelView = findViewById(R.id.vMain) // ✅ initialise d'abord levelView

        val level = intent.getIntExtra("LEVEL", 1)

        when (level) {
            1 -> setupLevel1()
            2 -> setupLevel2()
            3 -> setupLevel3()
            else -> setupLevel1()
        }

        button2 = findViewById(R.id.button2)
        gestureDetector = GestureDetector(this, this)

        button2.setOnClickListener {
            levelView.newgamebutton()
        }
        button3 = findViewById(R.id.button3)
        button3.setOnClickListener {
            val i = Intent(this@MainActivity, MainActivity2::class.java)
            this.finish()
            startActivity(i)

        }
        levelView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dragStartX = event.x
                    dragStartY = event.y
                    levelView.startArrow(dragStartX, dragStartY)
                }

                MotionEvent.ACTION_MOVE -> {
                    levelView.updateArrow(event.x, event.y)
                }

                MotionEvent.ACTION_UP -> {
                    val dragEndX = event.x
                    val dragEndY = event.y

                    val distance = hypot((dragEndX - dragStartX).toDouble(), (dragEndY - dragStartY).toDouble())

                    if (distance > 100) {
                        val (vx, vy) = levelView.computeLaunchVector(dragStartX, dragStartY, dragEndX, dragEndY)
                        levelView.shootbird(-vx.toDouble()*2.5, -vy.toDouble()*2.5)
                    }

                    levelView.hideArrow()
                    levelView.performClick()
                }
            }

            true
        }


    }

    override fun onPause() {
        super.onPause()
        levelView.pause()
    }

    override fun onResume() {
        super.onResume()
        levelView.resume()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }


    override fun onDown(p0: MotionEvent): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent) {
        return
    }

    override fun onSingleTapUp(p0: MotionEvent): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent) {
        return
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        try {
            val diffY = (e2.y - e1.y).toDouble()
            val diffX = (e2.x - e1.x).toDouble()
            //levelView.shootbird(diffX, diffY)
        }
        catch (exception: Exception) {
            exception.printStackTrace()
        }
        return true
    }

    override fun onDrag(p0: View?, p1: DragEvent?): Boolean {
        TODO("Not yet implemented")
    }


}