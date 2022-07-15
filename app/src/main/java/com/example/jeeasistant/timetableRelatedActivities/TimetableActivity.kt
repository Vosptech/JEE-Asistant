package com.example.jeeasistant.timetableRelatedActivities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jeeasistant.R
import com.example.jeeasistant.databinding.ActivityLoginBinding
import com.example.jeeasistant.databinding.ActivityTimetableBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.internal.http.HttpDate.format
import java.lang.reflect.Array
import java.sql.Types.ARRAY
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern
import javax.xml.transform.Source
import kotlin.collections.ArrayList

class TimetableActivity : AppCompatActivity() {
    val users = ArrayList<User>()
    private  lateinit var binding:ActivityTimetableBinding
    private lateinit var tvDate: TextView
    private lateinit var tvDay: TextView
    private val db = Firebase.firestore
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)
        binding = ActivityTimetableBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        actionBar?.hide()
        supportActionBar?.hide()
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val personEmail = acct?.email.toString()
// Get current date and day //////////////////////////////////////////////////////////////////////////////
        tvDate = findViewById(R.id.date)
        tvDay = findViewById(R.id.Day)
        val localDate = LocalDate.now()
        val date = (DateTimeFormatter.ofPattern("dd-MM-yyyy").format(localDate))
        val day = LocalDate.now().getDayOfWeek().name
        tvDate.text = date
        tvDay.text = day
// Get current date and day End //////////////////////////////////////////////////////////////////////////////
//add task onclick listner ///////////////////////////////////////////////////////////////////////////////////
        binding.extendedFab.setOnClickListener {
            val addTaskScreen = Intent(this, AddTaskActivity::class.java)
            startActivity(addTaskScreen)
        }
//add task onclick listner ///////////////////////////////////////////////////////////////////////////////////


        val docref =
            db.collection("Gusers").document(personEmail).collection("tasks").document(date)
        docref.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("hello", "DocumentSnapshot data: ${document.data}")
                    val namesOfTasks = document.get("TaskNames").toString()
//                    Toast.makeText(this,taskList, Toast.LENGTH_LONG).show()


                    // remove '[' ']' from string
                 val astr=namesOfTasks.replace("[","")
                    val bstr=astr.replace("]","")


                    //convert string to aray by seperating elements with ','
                    val taskNameArray: kotlin.Array<String> =  arrayOf(*bstr.split(",").toTypedArray())
                        Log.d("hello", "DocumentSnapshot modified: ${bstr}")
                    for (element in taskNameArray) {
                        Log.d("hello", "Array: ${element}")
                    }


                    val recyclerView = findViewById(R.id.rView) as RecyclerView

                    //adding a layoutmanager
                    recyclerView.layoutManager = LinearLayoutManager(this)
//                    recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


                    //crating an arraylist to store users using the data class user


                    // extracting elements from Array and puting it in arraylist
                   for (i in taskNameArray){
                       val x = User(i)
                       users.add(x)
                   }
                    val adapter = Myadapter(users)

                    //now adding the adapter to recyclerview
                    recyclerView.adapter = adapter

//                    users.add(User("item 1"))
//                    users.add(User(" itwm 2"))
//                    users.add(User("item 3"))
//                    users.add(User("item 4"))
                } else {
                    Log.d("doesnt exist", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("errordb", "get failed with ", exception)
            }


    }//oncreate end


}//app compact actictivity end