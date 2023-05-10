package com.example.angryducks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity(), GestureDetector.OnGestureListener {

    private lateinit var levelView: LevelView
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var gestureDetector: GestureDetector       //Cette classe sert d'intermédiaire avec l'utilisateur lorsque celui-ci est en jeu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        levelView = findViewById(R.id.vMain)
        button2 = findViewById(R.id.button2)
        gestureDetector = GestureDetector(this)
        button2.setOnClickListener {
            levelView.newgamebutton()
        }
        button3 = findViewById(R.id.button3)
        button3.setOnClickListener {
            val i = Intent(this@MainActivity, MainActivity2::class.java)
            this.finish()
            startActivity(i)

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
        return if (gestureDetector.onTouchEvent(event)) {
            true
        }
        else {
            super.onTouchEvent(event)
        }
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
            levelView.shootbird(diffX, diffY)
        }
        catch (exception: Exception) {
            exception.printStackTrace()
        }
        return true
    }


}