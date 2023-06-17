package com.niqr.todoapp.ui.tasks.recycler

import androidx.recyclerview.widget.DiffUtil
import com.niqr.todoapp.data.model.TodoItem

class DiffCallback : DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem) =
        oldItem == newItem
}