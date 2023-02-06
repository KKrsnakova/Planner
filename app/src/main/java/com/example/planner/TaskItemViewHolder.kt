package com.example.planner

import android.content.Context
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener

) : RecyclerView.ViewHolder(binding.root) {
    val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun bindTaskItem(taskItem: TaskItem) {
        binding.name.text = taskItem.name

        if (taskItem.isCompleted()) {
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.tfDate.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        }

        binding.btnComplete.setImageResource(taskItem.imageResource())
        binding.btnComplete.setColorFilter(taskItem.imageColor(context))

        binding.btnComplete.setOnClickListener {
            clickListener.completeTaskItem(taskItem)
        }
        binding.taskCellContainer.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }


        if (taskItem.dueTime() != null) {
            binding.dueTime.text = timeFormat.format(taskItem.dueTime())
            binding.tfDate.text = dateFormat.format(taskItem.date())

        } else {
            binding.dueTime.text = ""
            binding.tfDate.text = ""
        }

    }
}