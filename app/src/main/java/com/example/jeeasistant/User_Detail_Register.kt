package com.example.jeeasistant

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class User_Detail_Register : AppCompatActivity() {
    private lateinit var tvName:EditText
    private lateinit var tvDOB:EditText
    private lateinit var tvClass:EditText
    private lateinit var tvUsername:EditText
    private lateinit var tvPassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail_register)
        actionBar?.hide()
        supportActionBar?.hide()




        tvName = findViewById(R.id.regName)
        tvDOB = findViewById(R.id.regDOB)
        tvClass = findViewById(R.id.regStd)
        tvUsername = findViewById(R.id.regUserName)
        tvPassword = findViewById(R.id.regPwd)


        val db = Firebase.firestore

        findViewById<Button>(R.id.reg_Btn).setOnClickListener {
            val uName = tvName.text.toString().trim()
            val uDOB = tvDOB.text.toString().trim()
            val uClass = tvClass.text.toString().trim()
            val uUserName = tvUsername.text.toString().trim()
            val uPassword = tvPassword.text.toString().trim()
            val userMap = hashMapOf(
                "Name" to uName,
                "DOB" to uDOB,
                "Class" to uClass,
                "UserName" to uUserName,
                "Password" to uPassword
            )
            db.collection("users").document(uUserName).set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this,"Successfully Added",Toast.LENGTH_LONG).show()

                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }

        }




    }

}