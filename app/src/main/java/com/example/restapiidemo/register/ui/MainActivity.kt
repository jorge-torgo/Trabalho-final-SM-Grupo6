package com.example.restapiidemo.register.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.restapiidemo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registo)
        getSupportActionBar()?.hide()
        val textView3 = findViewById<TextView>(R.id.textView3)
        textView3.setOnClickListener {
            // intent to start activity
            val intent = Intent(this@MainActivity, com.example.restapiidemo.login.ui.MainActivity::class.java)
            startActivity(intent)
        }
    }
}