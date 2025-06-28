package com.example.angryducks

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        findViewById<Button>(R.id.button_level1).setOnClickListener {
            startLevel(1)
        }

        findViewById<Button>(R.id.button_level2).setOnClickListener {
            startLevel(2)
        }

        findViewById<Button>(R.id.button_level3).setOnClickListener {
            startLevel(3)
        }
    }

    private fun startLevel(level: Int) {
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("LEVEL", level) // <- Passe l’info du niveau ici
        startActivity(i)
    }
}
