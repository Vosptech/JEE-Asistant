package com.example.jeeasistant.timetableRelatedActivities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jeeasistant.R


class Myadapter(private val taskList : ArrayList<com.example.jeeasistant.timetableRelatedActivities.User>) : RecyclerView.Adapter<Myadapter.MyViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_timetabal,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: Myadapter.MyViewHolder, position: Int) {
        holder.bindItems(taskList[position])
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
    class MyViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
        fun bindItems(user: User) {
            val textViewName = itemView.findViewById(R.id.textViewName) as TextView
            textViewName.text = user.name

        }
    }
}