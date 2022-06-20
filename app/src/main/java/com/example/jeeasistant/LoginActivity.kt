package com.example.jeeasistant

import android.content.ContentProviderClient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var lemail: TextView
    private lateinit var lpwd: TextView
    private lateinit var lbtn: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        auth = Firebase.auth
        lemail = findViewById(R.id.loginemail)
        lpwd = findViewById(R.id.loginPwd)
        lbtn = findViewById(R.id.loginBtn)

        lbtn.setOnClickListener {
            val pemail = lemail.text.toString().trim()
            val ppwd = lpwd.text.toString().trim()
            auth.signInWithEmailAndPassword(pemail, ppwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }


        }


    }

    private fun updateUI(user: FirebaseUser?) {
        val next = Intent(this, MainActivity::class.java)
        startActivity(next)

    }

    fun registerUser(view: View) {
        val registertheuser = Intent(this, emailRegisterActivity::class.java)
        startActivity(registertheuser)

    }
}

