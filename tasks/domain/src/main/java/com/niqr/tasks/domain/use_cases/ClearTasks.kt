package com.niqr.tasks.domain.use_cases

import com.niqr.tasks.domain.repo.TodoItemsRepository

class ClearTasks(
    private val repo: TodoItemsRepository
) {
    suspend operator fun invoke() = repo.clearTodoItems()
}