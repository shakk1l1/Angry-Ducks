package com.example.angryducks

//import androidx.appcompat.app.AppCompatActivity
//import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity(), GestureDetector.OnGestureListener {

    lateinit var levelView: LevelView
    lateinit var button2: Button
    lateinit var button3: Button
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        levelView = findViewById<LevelView>(R.id.vMain)
        button2 = findViewById(R.id.button2)
        gestureDetector = GestureDetector(this)
        button2.setOnClickListener {
            levelView.cassepaslesc()
        }
        button3 = findViewById(R.id.button3)
        button3.setOnClickListener {
            val i = Intent(this@MainActivity, MainActivity2::class.java)
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