package com.example.sqlitecrud

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlitecrud.database.DatabaseHelper
import com.example.sqlitecrud.model.TaskListModel

class AddTask : AppCompatActivity(){

    lateinit var btn_save : Button
    lateinit var btn_delete : Button
    lateinit var et_name : EditText
    lateinit var et_details :EditText
    var dbHandler : DatabaseHelper ?= null
    var isEditMode : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        btn_save = findViewById(R.id.btn_save)
        btn_delete = findViewById(R.id.btn_delete)
        et_name = findViewById(R.id.et_name)
        et_details = findViewById(R.id.et_details)

        dbHandler = DatabaseHelper(this)

        if (intent != null && intent.getStringExtra("Mode") == "E"){
            //Update Data
            isEditMode = true
            btn_save.text = "Actualizar datos"
            btn_delete.visibility = View.GONE

            val tasks : TaskListModel = dbHandler!!.getTask(intent.getIntExtra("Id", 0))
            et_name.setText(tasks.name)
            et_details.setText(tasks.details)


        } else {
            isEditMode = false
            btn_save.text = "Guardar datos"
            btn_delete.visibility = View.GONE
        }

        btn_save.setOnClickListener{
            var success : Boolean = false
            val tasks : TaskListModel = TaskListModel()
            if (isEditMode) {
                //Update
                tasks.id = intent.getIntExtra("Id", 0)
                tasks.name = et_name.text.toString()
                tasks.details = et_details.text.toString()

                success = dbHandler?.updateTask(tasks) as Boolean

            } else {
                //insert
                tasks.name = et_name.text.toString()
                tasks.details = et_details.text.toString()

                success = dbHandler?.addTask(tasks) as Boolean
            }
            if (success){
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
                finish()
            } else {
              Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }
        }

        btn_delete.setOnClickListener{
            var i : Int
             val dialog = AlertDialog.Builder(this).setTitle("Aviso").setMessage("¿Quieres eliminar la tarea?")
                 .setPositiveButton("Sí") { dialog, i ->
                     val success = dbHandler!!.deleteTask(intent.getIntExtra("Id", 0)) as Boolean
                     if (success)
                         finish()
                     dialog.dismiss()
                 }
                 .setNegativeButton("No") { dialog, i ->
                     dialog.dismiss()
                 }
            dialog.show()
        }

    }
}