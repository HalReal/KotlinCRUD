package com.example.sqlitecrud.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sqlitecrud.model.TaskListModel

class DatabaseHelper (context : Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){
    companion object{
        private val DB_NAME = "task"
        private val DB_VERSION = 1
        private val TABLE_NAME = "tasklist"
        private val ID = "id"
        private val TASK_NAME = "taskname"
        private val TASK_DETAILS = "taskdetails"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $TASK_NAME TEXT, $TASK_DETAILS TEXT);"
        p0?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }

    fun getAllTask(): List<TaskListModel>{
        val tasklist = ArrayList<TaskListModel>()
        val db:SQLiteDatabase = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    val tasks = TaskListModel()
                    tasks.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    tasks.name = cursor.getString(cursor.getColumnIndex(TASK_NAME))
                    tasks.details = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
                    tasklist.add(tasks)

                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return tasklist

    }



    //insert
    fun addTask (tasks : TaskListModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TASK_NAME, tasks.name)
        values.put(TASK_DETAILS, tasks.details)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    //Seleccionar la fecha de un id en concreto
    fun getTask(_id : Int) : TaskListModel{
        val tasks = TaskListModel()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        tasks.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        tasks.name = cursor.getString(cursor.getColumnIndex(TABLE_NAME))
        tasks.details = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
        cursor.close()

        return tasks
    }

    fun deleteTask (_id: Int) : Boolean{
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") !=1
    }

    fun updateTask (tasks: TaskListModel) : Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TASK_NAME, tasks.name)
        values.put(TASK_DETAILS, tasks.details)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(tasks.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") !=1
    }
}