package com.example.todolistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.textViewTask.text = task.taskName
        holder.checkBox.isChecked = task.isCompleted
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
        }
    }

    override fun getItemCount(): Int = taskList.size

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val textViewTask: TextView = itemView.findViewById(R.id.textViewTask)
    }
}
