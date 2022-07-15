package com.example.jeeasistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.jeeasistant.databinding.ActivityEmailRegisterBinding
import com.example.jeeasistant.databinding.ActivityMainBinding
import com.example.jeeasistant.databinding.ActivityOtpVerifyBinding
import com.example.jeeasistant.loginRelatedActivities.UserDetailRegister
import com.google.firebase.auth.*


class OTP_verify : AppCompatActivity() {
//    private lateinit var digit1 : EditText
//    private lateinit var digit2 : EditText
//    private lateinit var digit3 : EditText
//    private lateinit var digit4 : EditText
//    private lateinit var digit5 : EditText
//    private lateinit var digit6 : EditText

    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var auth: FirebaseAuth
    private  lateinit var binding: ActivityOtpVerifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verify)
        binding = ActivityOtpVerifyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        actionBar?.hide()
        supportActionBar?.hide()


//        digit1 = findViewById(R.id.box1)
//        digit2 = findViewById(R.id.box2)
//        digit3 = findViewById(R.id.box3)
//        digit4 = findViewById(R.id.box4)
//        digit5 = findViewById(R.id.box5)
//        digit6 = findViewById(R.id.box6)


        auth=FirebaseAuth.getInstance()

        // get storedVerificationId from the intent
        val storedVerificationId= intent.getStringExtra("storedVerificationId")
        val number= intent.getStringExtra("number")
         binding.phDisplay.text = number
//         resendToken = token

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

                    val intent = Intent(this , UserDetailRegister::class.java)
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
//    private fun resendVerificationCode(phone: String, token: PhoneAuthProvider.ForceResendingToken?){
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phone) // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this) // Activity (for callback binding)
//            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
//            .setForceResendingToken(token)
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    fun resend_otp(view: View) {
//        resendVerificationCode()
//
//    }


}