package com.example.sqlitecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitecrud.adapter.TaskListAdapter
import com.example.sqlitecrud.database.DatabaseHelper
import com.example.sqlitecrud.model.TaskListModel

class MainActivity : AppCompatActivity() {

    lateinit var recycler_task : RecyclerView
    lateinit var btn_add : Button
    var taskListAdapter : TaskListAdapter ?= null
    var dbHandler : DatabaseHelper ?= null
    var tasklist : List<TaskListModel> = ArrayList<TaskListModel>()
    var linearlayoutManager : LinearLayoutManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_task = findViewById(R.id.rv_list)
        btn_add = findViewById(R.id.ft_add_items)

        dbHandler = DatabaseHelper(this)
        fetchlist()

        btn_add.setOnClickListener{
            val i = Intent(applicationContext, AddTask::class.java)
            startActivity(i)
        }
    }

    private fun fetchlist(){
        tasklist = dbHandler!!.getAllTask()
        taskListAdapter = TaskListAdapter(tasklist,applicationContext)
        linearlayoutManager = LinearLayoutManager(applicationContext)
        recycler_task.layoutManager = linearlayoutManager
        recycler_task.adapter = taskListAdapter
        taskListAdapter?.notifyDataSetChanged()
    }
}