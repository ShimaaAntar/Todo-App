package com.example.todo

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.todo.database.TaskDatabase
import com.example.todo.database.model.Task
import com.example.todo.databinding.FragmentDialogeBinding



class DetailsDialogeFragment(val position: Int,val task: Task) : DialogFragment() {

    lateinit var binding: FragmentDialogeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDialogeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title =TaskDatabase.getInstance(requireContext()).taskDao().readTask(task.title.toString()).title
        binding.titleFrag.setText(title.toString())
        val description =TaskDatabase.getInstance(requireContext()).taskDao().readTask(task.title.toString()).description
        binding.descFrag.setText(description.toString())
        val completed =TaskDatabase.getInstance(requireContext()).taskDao().readTask(task.title.toString()).isCompleted
        binding.completedFrag.isChecked=completed?:false
    }
    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.getWindow()?.setLayout(width, height)

        }
    }


}