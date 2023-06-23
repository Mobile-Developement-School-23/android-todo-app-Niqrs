package com.niqr.todoapp.ui.tasks.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.niqr.todoapp.R
import com.niqr.todoapp.data.model.TodoItem
import com.niqr.todoapp.compose.ui.tasks.model.TasksAction

class TodoItemsAdapter(
    private val onUiAction: (TasksAction) -> Unit
) : ListAdapter<TodoItem, TodoItemViewHolder>(DiffCallback()) {

    public override fun getItem(position: Int): TodoItem {
        return super.getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TodoItemViewHolder(
            layoutInflater.inflate(
                R.layout.todo_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.onBind(
            getItem(position),
            onUiAction
        )
    }
}