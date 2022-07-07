package com.example.jeeasistant.loginRelatedActivities

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.jeeasistant.MainActivity
import com.example.jeeasistant.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class UserDetailRegister : AppCompatActivity() {
    private lateinit var tvName:EditText
    private lateinit var tvDOB:EditText
    private lateinit var tvBio:EditText
    private lateinit var tvUsername:EditText
    private val db = Firebase.firestore
    private var mGoogleSignInClient : GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail_register)
        actionBar?.hide()
        supportActionBar?.hide()




        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        tvName = findViewById(R.id.regName)
        tvDOB = findViewById(R.id.regDOB)
        tvBio = findViewById(R.id.regBio)
        tvUsername = findViewById(R.id.regUserName)





        val mycalendar = Calendar.getInstance()
        val datepicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
            mycalendar.set(Calendar.YEAR, year)
            mycalendar.set(Calendar.MONTH, month)
            mycalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(mycalendar)
        }
        tvDOB.setOnClickListener{
            DatePickerDialog(this,datepicker, mycalendar.get(Calendar.YEAR), mycalendar.get(Calendar.MONTH) ,
                mycalendar.get(Calendar.DAY_OF_MONTH)).show()
        }





        findViewById<Button>(R.id.reg_Btn).setOnClickListener {
            val uName = tvName.text.toString().trim()
            val uDOB = tvDOB.text.toString().trim()
            val uBio = tvBio.text.toString().trim()
            val uUserName = tvUsername.text.toString().trim()

            val ref = db.collection("userNames").document(uUserName)
            ref.get().addOnSuccessListener {

                if(it != null){
                    val un = it.data.toString()
                    Toast.makeText(this,un,Toast.LENGTH_LONG).show()
                    if(un == "null"){
                        Toast.makeText(this,"username available",Toast.LENGTH_LONG).show()
                        val acct = GoogleSignIn.getLastSignedInAccount(this)
                        if(acct != null) {
                            val personEmail = acct.email.toString()

                            val gusersMap = hashMapOf(
                                "Name" to uName,
                                "DOB" to uDOB,
                                "Bio" to uBio,
                                "UserName" to uUserName,
                                "Email" to personEmail
                            )
                            db.collection("Gusers").document(personEmail).set(gusersMap)


                                .addOnSuccessListener {
                                    val userNamesMap = hashMapOf(
                                        "UserName" to uUserName
                                    )
                                    db.collection("userNames").document(uUserName).set(userNamesMap)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "Successfully Added",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            val intent = Intent(this, MainActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w(TAG, "Error adding document", e)
                                            signOut()
//                                            val intent = Intent(this, LoginActivity::class.java)
//                                            startActivity(intent)
                                            Toast.makeText(this,"Something went wrong,Please try again",Toast.LENGTH_LONG).show()
                                        }


                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Error adding document", e)
                                    signOut()
//                                    val intent = Intent(this, LoginActivity::class.java)
//                                    startActivity(intent)
                                    Toast.makeText(this,"Something went wrong,Please try again",Toast.LENGTH_LONG).show()
                                }
                        }



                    }
                    else{
                        Toast.makeText(this,"try another user name ",Toast.LENGTH_LONG).show()

                    }
                }
                else{
                    Toast.makeText(this,"Something went wrong,Please try again",Toast.LENGTH_LONG).show()
//                    val intent = Intent(this, LoginActivity::class.java)
//                    startActivity(intent)
                    signOut()


                }

            }
            OnFailureListener{
                Toast.makeText(this,"Something went wrong,Please try again",Toast.LENGTH_LONG).show()
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
                signOut()

            }



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

    private fun updateLable(mycalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDOB.setText(sdf.format(mycalendar.time))
    }

    fun signOut(view: View) {
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