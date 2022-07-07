package com.example.jeeasistant.loginRelatedActivities

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.jeeasistant.MainActivity

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CheckIfUserAccAlreadyExits : AppCompatActivity() {
    private val db = Firebase.firestore
    private var mGoogleSignInClient : GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.jeeasistant.R.layout.activity_check_if_user_acc_already_exits)
        actionBar?.hide()
        supportActionBar?.hide()

        val pb = findViewById<View>(com.example.jeeasistant.R.id.pbLoading) as ProgressBar
        pb.visibility = ProgressBar.VISIBLE


        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) { //here the variable lEmail will store the email
            val lEmail = acct.email.toString()
            val ref = db.collection("Gusers").document(lEmail)
            ref.get().addOnSuccessListener {

                if (it != null) {
                    val dbEmail = it.data?.get("Email")?.toString()


                    if (dbEmail == lEmail){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        val gotoReg = Intent(this,UserDetailRegister::class.java)
                        startActivity(gotoReg)
                        finish()

                    }
                }
            }

        }
        else{
//            val previousScreen = Intent(this,LoginActivity::class.java)
//            startActivity(previousScreen)
            signOut()

        }
    }

    private fun signOut() {
        mGoogleSignInClient?.signOut()?.addOnCompleteListener(this, object:
            OnCompleteListener<Void> {
            override fun onComplete(p0: Task<Void>) {

            }
        })
        Firebase.auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

