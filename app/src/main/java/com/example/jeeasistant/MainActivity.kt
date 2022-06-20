package com.example.jeeasistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlin.io.print as print

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()



    }

    fun nextscreen(view: View) {
        val intent = Intent(this, emailRegisterActivity::class.java)
        startActivity(intent)
    }
}