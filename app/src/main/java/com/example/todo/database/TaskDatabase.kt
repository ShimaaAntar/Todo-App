package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.database.dao.TasksDao
import com.example.todo.database.model.Task

@Database(entities = [Task::class],version=1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao():TasksDao

    companion object{
        private var database:TaskDatabase?=null
        private val DATABASE_NAME="task_db"
        fun getInstance(context: Context):TaskDatabase{
            if (database==null){
                database= Room.databaseBuilder(context,TaskDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return database!!
        }
    }
}