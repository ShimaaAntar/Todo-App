package com.example.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.database.model.Task


class TaskAdapter(var tasks:List<Task>):RecyclerView.Adapter<TaskAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val completed: CheckBox = itemView.findViewById(R.id.completed)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task_layout, parent, false)
        return ViewHolder(view)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks.get(position)
        holder.title.setText(task.title)
        holder.completed.isChecked = task.isCompleted ?: false
        if (onTaskClickListener!=null){
            holder.itemView.setOnClickListener{
                onTaskClickListener?.onTaskClick(position, task)
            }
        }
        if (onLongClickListener!=null){
            holder.itemView.setOnLongClickListener {
                onLongClickListener?.onLongClick(position,task)
                return@setOnLongClickListener true
            }

        }
    }
    var onLongClickListener:OnLongClickListener?=null
    interface OnLongClickListener{
        fun onLongClick(position: Int,tasks: Task)
    }
    var onTaskClickListener:OnTaskClickListener?=null
    interface OnTaskClickListener{
        fun onTaskClick( position: Int,tasks: Task)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun changeData(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()//refresh layout
    }
}






