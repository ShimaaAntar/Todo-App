package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.database.TaskDatabase
import com.example.todo.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var taskadapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addBt.setOnClickListener {
            openAddTaskActivity()
        }
        taskadapter = TaskAdapter(listOf())


    }
    private fun openAddTaskActivity() {
        val intent = Intent(this, AddTasksActivity::class.java)
        startActivity(intent)
    }
    override fun onStart() {
        super.onStart()
        val tasks =
            TaskDatabase.getInstance(applicationContext)
                .taskDao()
                .getAllTask()
        taskadapter.changeData(tasks)
    }


}