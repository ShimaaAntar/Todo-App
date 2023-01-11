package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.base.BaseActivity
import com.example.todo.database.TaskDatabase
import com.example.todo.databinding.ActivityHomeBinding


class HomeActivity : BaseActivity(){
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
        binding.recyclerView.adapter=taskadapter
        swipeTODelete()


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

    private fun swipeTODelete() = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val item = taskadapter.tasks.get(position)
            showDialoge(message = "you want delete task",
                posActionName = "Undo", posAction = { dialog, which ->
                    TaskDatabase.getInstance(applicationContext)
                        .taskDao()
                        .addTask(item)
                    val tasks =
                        TaskDatabase.getInstance(applicationContext)
                            .taskDao()
                            .getAllTask()
                    taskadapter.changeData(tasks)

                    dialog.dismiss()
                }, deleteAction = {
                    TaskDatabase.getInstance(applicationContext)
                        .taskDao()
                        .deleteTask(item)
                    val tasks =
                        TaskDatabase.getInstance(applicationContext)
                            .taskDao()
                            .getAllTask()
                    taskadapter.changeData(tasks)
                })
            Handler(Looper.getMainLooper()).postDelayed({
                val item = taskadapter.tasks.get(position)
                recreate()
                TaskDatabase.getInstance(applicationContext)
                    .taskDao()
                    .deleteTask(item)
                val tasks =
                    TaskDatabase.getInstance(applicationContext)
                        .taskDao()
                        .getAllTask()
                taskadapter.changeData(tasks)
            }, 2000)
        }
    }).attachToRecyclerView(binding.recyclerView)

}