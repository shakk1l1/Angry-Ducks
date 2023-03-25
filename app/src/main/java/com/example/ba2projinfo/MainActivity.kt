package com.example.ba2projinfo

//import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {

    lateinit var canonView: CanonView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        canonView = findViewById<CanonView>(R.id.vMain)
    }

    override fun onPause() {
        super.onPause()
        canonView.pause()
    }

    override fun onResume() {
        super.onResume()
        canonView.resume()
    }
}