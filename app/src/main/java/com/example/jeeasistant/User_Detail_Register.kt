package com.example.jeeasistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class User_Detail_Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail_register)
        actionBar?.hide()
        supportActionBar?.hide()
    }
}