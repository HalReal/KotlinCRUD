package com.example.sqlitecrud.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitecrud.AddTask
import com.example.sqlitecrud.R
import com.example.sqlitecrud.model.TaskListModel

class TaskListAdapter(tasklist : List<TaskListModel>, internal var context : Context)
    : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>(){

        internal var tasklist : List<TaskListModel> = ArrayList()
    init {
        this.tasklist = tasklist
    }
        inner class TaskViewHolder(view : View) : RecyclerView.ViewHolder(view){
            var name : TextView = view.findViewById(R.id.et_name)
            var details : TextView = view.findViewById(R.id.et_details)
            var btn_edit : TextView = view.findViewById(R.id.btn_edit)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.TaskViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.recycler_task_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListAdapter.TaskViewHolder, position: Int) {
        val tasks = tasklist[position]
        holder.name.text = tasks.name
        holder.details.text = tasks.details

        holder.btn_edit.setOnClickListener{
            val i = Intent(context, AddTask::class.java)
            i.putExtra("Mode", "E")
            i.putExtra("Id", tasks.id)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return tasklist.size
    }

}