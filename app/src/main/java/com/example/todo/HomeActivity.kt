package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.database.TaskDatabase
import com.example.todo.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addBt.setOnClickListener {
            openAddTaskActivity()
        }

    }
    private fun openAddTaskActivity() {
        val intent = Intent(this, AddTasksActivity::class.java)
        startActivity(intent)
    }


}