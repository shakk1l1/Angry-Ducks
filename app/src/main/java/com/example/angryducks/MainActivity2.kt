package com.example.angryducks

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val i = Intent(this@MainActivity2, MainActivity::class.java)
            startActivity(i)
        }
        var mediaPlayer = MediaPlayer.create(applicationContext, R.raw.themesong)
        mediaPlayer.start() // no need to call prepare(); create() does that for you
    }
}