package com.example.angryducks

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val i = Intent(this@MainActivity2, MainActivity::class.java)
            startActivity(i)

        }

    }
}