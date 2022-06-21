package com.example.jeeasistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_otp_verify.*

class OTP_verify : AppCompatActivity() {
//    private lateinit var digit1 : EditText
//    private lateinit var digit2 : EditText
//    private lateinit var digit3 : EditText
//    private lateinit var digit4 : EditText
//    private lateinit var digit5 : EditText
//    private lateinit var digit6 : EditText


    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verify)


//        digit1 = findViewById(R.id.box1)
//        digit2 = findViewById(R.id.box2)
//        digit3 = findViewById(R.id.box3)
//        digit4 = findViewById(R.id.box4)
//        digit5 = findViewById(R.id.box5)
//        digit6 = findViewById(R.id.box6)


        auth=FirebaseAuth.getInstance()

        // get storedVerificationId from the intent
        val storedVerificationId= intent.getStringExtra("storedVerificationId")

        // fill otp and call the on click on button
        findViewById<Button>(R.id.verify_btn).setOnClickListener {
//            val otp = "${digit1.text.toString().trim()}" +
//                    "${digit2.text.toString().trim()}" +
//                    "${digit3.text.toString().trim()}" +
//                    "${digit4.text.toString().trim()}" +
//                    "${digit5.text.toString().trim()}" +
//                    "${digit6.text.toString().trim()}"

            val otp = findViewById<EditText>(R.id.box1).text.trim().toString()
            if(otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // verifies if the code matches sent by firebase
    // if success start the new activity in our case it is main Activity
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val intent = Intent(this , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}