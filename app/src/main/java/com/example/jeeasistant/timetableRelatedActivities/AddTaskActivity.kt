package com.example.jeeasistant.timetableRelatedActivities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.jeeasistant.R
import com.example.jeeasistant.databinding.ActivityAddTaskBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*


class AddTaskActivity : AppCompatActivity() {
    private lateinit var tvTask: EditText
    private lateinit var tvDate: EditText
    private lateinit var addBtn: Button
    private val db = Firebase.firestore

    private  lateinit var binding:ActivityAddTaskBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        actionBar?.hide()
        supportActionBar?.hide()
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val progressBar = binding.progressBar1
        progressBar.visibility = View.GONE
        tvDate = findViewById(R.id.TaskDate)
        addBtn = findViewById(R.id.addTask)
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val personEmail = acct?.email.toString()
        val mycalendar = Calendar.getInstance()
        val datepicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
            mycalendar.set(Calendar.YEAR, year)
            mycalendar.set(Calendar.MONTH, month)
            mycalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd-MM-yyyy"

            val sdf = SimpleDateFormat(myFormat, Locale.UK)
            tvDate.setText(sdf.format(mycalendar.time))

        }
        tvDate.setOnClickListener {
            DatePickerDialog(
                this, datepicker, mycalendar.get(Calendar.YEAR), mycalendar.get(Calendar.MONTH),
                mycalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        addBtn.setOnClickListener {
             val progressBar = binding.progressBar1
            progressBar.visibility = View.VISIBLE
            val acc = GoogleSignIn.getLastSignedInAccount(this)
            if (acct != null) {
                val personemail = acc?.email.toString()
                tvTask = findViewById(R.id.TaskName)
                val taskName = tvTask.text.toString()
                val taskNameArray: Array<String> = arrayOf(taskName)
                val dayFormat = "dd"
                val monthFormat = "MM"
                val yearFormat = "yyyy"
                val myFormat = "dd-MM-yyyy"
                val sdf2 = SimpleDateFormat(dayFormat, Locale.UK)
                val sdf3 = SimpleDateFormat(monthFormat, Locale.UK)
                val sdf4 = SimpleDateFormat(yearFormat, Locale.UK)
                val cday = sdf2.format(mycalendar.time).toInt()
                val cmonth = sdf3.format(mycalendar.time).toInt()
                val cyear = sdf4.format(mycalendar.time).toInt()
                val period = Period.of(0, 0, 0)
                val period1 = Period.of(0, 0, 1)
                val period2 = Period.of(0, 0, 15)
                val period3 = Period.of(0, 1, 0)
                val period4 = Period.of(0, 3, 0)
                val selectedDate = LocalDate.of(cyear, cmonth, cday)
                val modifiedDate = selectedDate.plus(period)
                val modifiedDate1 = selectedDate.plus(period1)
                val modifiedDate2 = selectedDate.plus(period2)
                val modifiedDate3 = selectedDate.plus(period3)
                val modifiedDate4 = selectedDate.plus(period4)
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                val formatedDate = modifiedDate.format(formatter)
                val formatedDate1 = modifiedDate1.format(formatter)
                val formatedDate2 = modifiedDate2.format(formatter)
                val formatedDate3 = modifiedDate3.format(formatter)
                val formatedDate4 = modifiedDate4.format(formatter)
                val ref = db.collection("Gusers").document(personEmail).collection("tasks")
                    .document(formatedDate)
                ref.get().addOnSuccessListener {
                    if (it != null) {
                        val dbrespond = it.data.toString()
                        if (dbrespond == "null") {
                            val reg11 = HashMap<String, Any>()
                            reg11["TaskNames"] = Arrays.asList(*taskNameArray)
                            db.collection("Gusers").document(personEmail).collection("tasks")
                                .document(formatedDate).set(reg11)
                        } else {
                            val updateArrya =
                                db.collection("Gusers").document(personEmail).collection("tasks").document(formatedDate)
                            updateArrya.update("TaskNames", FieldValue.arrayUnion(taskName))
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "something went wrong ,please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                val ref1 = db.collection("Gusers").document(personEmail).collection("tasks")
                    .document(formatedDate1)
                ref1.get().addOnSuccessListener {
                    if (it != null) {
                        val dbrespond = it.data.toString()
                        if (dbrespond == "null") {
                            val reg11 = HashMap<String, Any>()
                            reg11["TaskNames"] = Arrays.asList(*taskNameArray)
                            db.collection("Gusers").document(personEmail).collection("tasks").document(formatedDate1).set(reg11)
                        } else {

                            val updateArrya =
                                db.collection("Gusers").document(personEmail).collection("tasks").document(formatedDate1)
                            updateArrya.update("TaskNames", FieldValue.arrayUnion(taskName))
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "something went wrong ,please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                val ref2 = db.collection("Gusers").document(personEmail).collection("tasks")
                    .document(formatedDate2)
                ref2.get().addOnSuccessListener {
                    if (it != null) {
                        val dbrespond = it.data.toString()
                        if (dbrespond == "null") {
                            val reg11 = HashMap<String, Any>()
                            reg11["TaskNames"] = Arrays.asList(*taskNameArray)
                            db.collection("Gusers").document(personEmail).collection("tasks")
                                .document(formatedDate2).set(reg11)
                        } else {
                            val updateArrya =
                                db.collection("Gusers").document(personEmail).collection("tasks").document(formatedDate2)
                            updateArrya.update("TaskNames", FieldValue.arrayUnion(taskName))
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "something went wrong ,please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                val ref3 = db.collection("Gusers").document(personEmail).collection("tasks")
                    .document(formatedDate3)
                ref3.get().addOnSuccessListener {
                    if (it != null) {
                        val dbrespond = it.data.toString()
                        if (dbrespond == "null") {
                            val reg11 = HashMap<String, Any>()
                            reg11["TaskNames"] = Arrays.asList(*taskNameArray)
                            db.collection("Gusers").document(personEmail).collection("tasks")
                                .document(formatedDate3).set(reg11)
                        } else {
                            val updateArrya =
                                db.collection("Gusers").document(personEmail).collection("tasks").document(formatedDate3)
                            updateArrya.update("TaskNames", FieldValue.arrayUnion(taskName))
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "something went wrong ,please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                val ref4 = db.collection("Gusers").document(personEmail).collection("tasks")
                    .document(formatedDate4)
                ref4.get().addOnSuccessListener {
                    if (it != null) {
                        val dbrespond = it.data.toString()
                        if (dbrespond == "null") {
                            val reg11 = HashMap<String, Any>()
                            reg11["TaskNames"] = Arrays.asList(*taskNameArray)
                            db.collection("Gusers").document(personEmail).collection("tasks")
                                .document(formatedDate4).set(reg11)
                        } else {
                            val updateArrya =
                                db.collection("Gusers").document(personEmail).collection("tasks").document(formatedDate4)
                            updateArrya.update("TaskNames", FieldValue.arrayUnion(taskName))
                        }
                        progressBar.visibility = View.GONE
                        tvTask.setText("")
                        tvDate.setText("")
                        Toast.makeText(this,"Task Added",Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(
                            this,
                            "something went wrong ,please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }



            }
            else {
                Toast.makeText(
                    this,
                    "something went wrong ,please try again",
                    Toast.LENGTH_LONG
                ).show()
        }


//            Toast.makeText(this,"1:$formatedDate1 ,2: $formatedDate2 ,3: $formatedDate3 ,4: $formatedDate4", Toast.LENGTH_LONG).show()
              }


        }

    fun back(view: View) {
        val intent = Intent(this,TimetableActivity::class.java)
        startActivity(intent)
        finish()
    }
}
//    private fun updateLable(mycalendar: Calendar) {
//        val myFormat = "dd-MM-yyyy"
//        val dayFormat = "dd"
//        val monthFormat = "MM"
//        val yearFormat = "yyyy"
//        val sdf = SimpleDateFormat(myFormat, Locale.UK)
//        tvDate.setText(sdf.format(mycalendar.time))
//        val sdf2 = SimpleDateFormat(dayFormat, Locale.UK)
//        val sdf3 = SimpleDateFormat(monthFormat, Locale.UK)
//        val sdf4 = SimpleDateFormat(yearFormat, Locale.UK)
//        val day = sdf2.format(mycalendar.time)
//        val month = sdf3.format(mycalendar.time)
//        val year = sdf4.format(mycalendar.time)
//        }

