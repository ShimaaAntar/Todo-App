package com.example.todo

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.todo.base.BaseActivity
import com.example.todo.database.TaskDatabase
import com.example.todo.database.model.Task
import com.example.todo.databinding.ActivityEditBinding
import com.example.todoapp.constance.Constance


class EditActivity: BaseActivity() {

    lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        val taskName = intent.getStringExtra(Constance.EXTRA_TASK)
        val taskId = intent.getIntExtra(Constance.EXTRA_ID,0)
        val title =TaskDatabase.getInstance(applicationContext).taskDao().readTask(taskName!!).title
        binding.title.setText(title.toString())
        val description =TaskDatabase.getInstance(applicationContext).taskDao().readTask(taskName).description
        binding.desc.setText(description.toString())
        val completed =TaskDatabase.getInstance(applicationContext).taskDao().readTask(taskName).isCompleted
        binding.completed.isChecked=completed?:false
        binding.editBt.setOnClickListener{
            EditTasts(taskId)
        }
    }
    private fun setupViews(){
        binding.titleLayout.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.titleLayout.error=null
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.descLayout.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.descLayout.error=null
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun EditTasts(taskId: Int) {
        if (!validData())return
        val taskTitle=binding.titleLayout.editText?.text.toString()
        val taskDesc=binding.descLayout.editText?.text.toString()
        val task= Task(id = taskId,title = taskTitle, description = taskDesc,
            isCompleted = binding.completed.isChecked)
        TaskDatabase.getInstance(applicationContext)
            .taskDao()
            .updateTask(task)
        showDialoge(messageId = R.string.tasts_added_successfully,
            posActionName = R.string.ok,
            posAction = DialogInterface.OnClickListener{ dialog, which->
                dialog.dismiss()
                finish()
            })
    }

    private fun validData(): Boolean {
        var isValid=true
        if (binding.titleLayout.editText?.text.toString().isBlank()){
            isValid=false
            binding.titleLayout.error="please enter valid title"
        }
        if (binding.descLayout.editText?.text.toString().isBlank()){
            isValid=false
            binding.descLayout.error="please enter valid description"
        }
        return isValid
    }

}


