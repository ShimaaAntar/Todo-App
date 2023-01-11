package com.example.todo

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import com.example.todo.base.BaseActivity
import com.example.todo.database.TaskDatabase
import com.example.todo.database.model.Task
import com.example.todo.databinding.ActivityAddTaskBinding


class AddTasksActivity:BaseActivity() {
    lateinit var binding: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        binding.saveBt.setOnClickListener{
            addTask()
        }
    }

    private fun addTask(){
        if (!isValid()) return
        val title=binding.titleLayout.editText?.text.toString()
        val desc=binding.descLayout.editText?.text.toString()
        val completed=binding.completed.isChecked

        val tasks=Task(title=title, description = desc, isCompleted = completed)
            TaskDatabase.getInstance(applicationContext)
            .taskDao()
            .addTask(tasks)
        showDialoge(messageId = R.string.tasts_added_successfully,
            posActionName = R.string.ok,
            posAction = DialogInterface.OnClickListener{ dialog, which->
                dialog.dismiss()
                finish()
            })

    }

    private fun isValid(): Boolean {
        var isvalid=true
        val titleTask=binding.titleLayout.editText?.text.toString().isBlank()
        val descTask=binding.descLayout.editText?.text.toString().isBlank()
        if (titleTask){
            isvalid=false
            binding.titleLayout.error = "the title is not valid"
            }
        if (descTask){
            isvalid=false
            binding.descLayout.error = "the description is not valid"
        }
        return isvalid
    }

    private fun setupViews(){
        binding.titleLayout.editText?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.titleLayout.error=null
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.descLayout.editText?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.descLayout.error=null
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

}