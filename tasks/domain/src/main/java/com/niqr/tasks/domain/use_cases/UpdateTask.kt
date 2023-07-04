package com.niqr.tasks.domain.use_cases

import com.niqr.tasks.domain.model.TodoItem
import com.niqr.tasks.domain.repo.TodoItemsRepository

class UpdateTask(
    private val repo: TodoItemsRepository
) {
    suspend operator fun invoke(task: TodoItem) = repo.updateTodoItem(task)
}