package com.example.restapiidemo.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.restapiidemo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val saveBtn = findViewById<Button>(R.id.saveBtn)
        saveBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, com.example.restapiidemo.home.ui.MainActivity::class.java)
            startActivity(intent)
        }

        val textView3 = findViewById<TextView>(R.id.textView3)
        textView3.setOnClickListener {
            val intent = Intent(this@MainActivity, com.example.restapiidemo.register.ui.MainActivity::class.java)
            startActivity(intent)
        }
    }
}