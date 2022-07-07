package com.example.jeeasistant

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jeeasistant.loginRelatedActivities.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()  {

    private var mGoogleSignInClient : GoogleSignInClient? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        auth = Firebase.auth

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(this)


        if(acct != null) {
            val personName = acct.displayName    //here the variable personName wil store the display name
            person_name.text = personName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            person_email.text = personEmail
            val personId = acct.id
            person_id.text = personId
            val personPhoto: Uri? = acct.photoUrl
        }



    }
    fun signOut(){
        mGoogleSignInClient?.signOut()?.addOnCompleteListener(this, object: OnCompleteListener<Void>{
            override fun onComplete(p0: Task<Void>) {
                Toast.makeText(this@MainActivity, "Signed Out", Toast.LENGTH_LONG).show()
            }
        })
       Firebase.auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()

    }

    fun signOut(view: View) {
        signOut()
    }


}