package com.example.jeeasistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class emailRegisterActivity : AppCompatActivity() {
    private lateinit var tvname:TextView
    private lateinit var tvdob:TextView
    private lateinit var tvstd:TextView
    private lateinit var tvemail:TextView
    private lateinit var tvpassword:TextView
    private lateinit var tvreg:Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_email_register)
        supportActionBar?.hide()
        auth = Firebase.auth


        tvname = findViewById(R.id.emailName)
        tvdob = findViewById(R.id.emailDOB)
        tvstd = findViewById(R.id.emailStd)
        tvemail = findViewById(R.id.email)
        tvpassword = findViewById(R.id.emailPwd)
        tvreg = findViewById(R.id.registerBtn)

        tvreg.setOnClickListener {

            val semail = tvemail.text.toString().trim()
            val spwd = tvpassword.text.toString().trim()

            auth.createUserWithEmailAndPassword(semail, spwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }



   }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)

    }
}
