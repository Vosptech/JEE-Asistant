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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN = 73
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var lemail: TextView
    private lateinit var lpwd: TextView
    private lateinit var lbtn: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        actionBar?.hide()
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
                        updateUI(null)

                    }
                }


        }

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        sign_in_button.setOnClickListener {
            signIn()
        }
    } //OnCreate function ends here

    private fun updateUI(user: FirebaseUser?) {
        val next = Intent(this, MainActivity::class.java)
        startActivity(next)

    }
    fun registerUser(view: View) {
        val registertheuser = Intent(this, emailRegisterActivity::class.java)
        startActivity(registertheuser)

    }


    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if(account!=null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        val intent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account : GoogleSignInAccount? = task.getResult(ApiException::class.java)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.

            }
        }
    }
}

