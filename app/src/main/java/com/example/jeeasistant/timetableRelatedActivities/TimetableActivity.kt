package com.example.jeeasistant.timetableRelatedActivities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jeeasistant.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.internal.http.HttpDate.format
import kotlinx.android.synthetic.main.activity_timetable.*
import java.lang.reflect.Array
import java.sql.Types.ARRAY
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.xml.transform.Source

class TimetableActivity : AppCompatActivity() {
    private lateinit var tvDate: TextView
    private lateinit var tvDay: TextView
    private val db = Firebase.firestore
    private lateinit var  newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Tasks>
    lateinit var heading : kotlin.Array<String>
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)
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
        extended_fab.setOnClickListener {
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
                 val Astr=namesOfTasks.replace("[","")
                    val Bstr=Astr.replace("]","")
                    val Cstr=Bstr.replace(",","\",\"")
                    val ModifiedTaskName="\"$Cstr\""
                    Log.d("hello", "DocumentSnapshot modified: ${ModifiedTaskName}")
                    heading = arrayOf(ModifiedTaskName)

                } else {
                    Log.d("doesnt exist", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("errordb", "get failed with ", exception)
            }

        newRecyclerView = findViewById(R.id.rView)
        newRecyclerView.layoutManager= LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf<Tasks>()
        getUserdata()
    }//oncreate end

    private fun getUserdata(){
        for (i in heading.indices){
            val task = Tasks(heading[i])
            newArrayList.add(task)
        }
        newRecyclerView.adapter = Myadapter(newArrayList)
    }
}//app compact actictivity end