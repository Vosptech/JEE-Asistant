package com.example.jeeasistant

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jeeasistant.databinding.ActivityEmailRegisterBinding
import com.example.jeeasistant.databinding.ActivityMainBinding
import com.example.jeeasistant.databinding.ActivityTimetableBinding
import com.example.jeeasistant.loginRelatedActivities.LoginActivity
import com.example.jeeasistant.timetableRelatedActivities.TimetableActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
class MainActivity : AppCompatActivity()  {

    private var mGoogleSignInClient : GoogleSignInClient? = null
    private lateinit var auth: FirebaseAuth
    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
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
            binding.personEmail.text = personName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            binding.personEmail.text = personEmail
            val personId = acct.id
            binding.personId.text = personId
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

    fun scheduleScreen(view: View) {
        val schedule = Intent(this,TimetableActivity::class.java)
        startActivity(schedule)
    }


}