package com.example.jeeasistant

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.jeeasistant.databinding.ActivityEmailRegisterBinding
import com.example.jeeasistant.databinding.ActivityLoginBinding
import com.example.jeeasistant.databinding.ActivityTimetableBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class emailRegisterActivity : AppCompatActivity() {
    var number: String = ""

    // create instance of firebase auth
    lateinit var auth: FirebaseAuth
    // we will use this to match the sent otp from firebase
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private  lateinit var binding: ActivityEmailRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_register)
        binding = ActivityEmailRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()



        // start verification on click of the button
        findViewById<Button>(R.id.registerBtn).setOnClickListener {
            val phNo = findViewById<EditText>(R.id.phone).text.toString().trim()

            if(phNo.isEmpty()){
                binding.phone.error ="Phone Number Required "
                return@setOnClickListener
            }else{
            login()
        }
        }
        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            // This method is called when the verification is completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
                Log.d("GFG", "onVerificationCompleted Success")
            }
            // Called when verification is failed add log statement to see the exception
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("GFG", "onVerificationFailed  $e")
            }
            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("GFG", "onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token

                // Start a new activity using intent
                // also send the storedVerificationId using intent
                // we will use this id to send the otp back to firebase
                val intent = Intent(applicationContext, OTP_verify::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                intent.putExtra("resendToken", resendToken)
                intent.putExtra("number", number)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun updateUI(user: FirebaseUser?) {
        val nextscr = Intent(this, MainActivity::class.java)
        startActivity(nextscr)

    }

    private fun login() {
        number = findViewById<EditText>(R.id.phone).text.trim().toString()
        // get the phone number from edit text and append the country cde with it
        if (number.isNotEmpty()) {
            number = "+91$number"
            sendVerificationCode(number)
        } else {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }
    // this method sends the verification code
    // and starts the callback of verification
    // which is implemented above in onCreate
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("GFG", "Auth started")
    }
}




