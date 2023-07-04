package com.niqr.tasks.domain.use_cases

import com.niqr.tasks.domain.repo.TodoItemsRepository

class RefreshTasks(
    private val repo: TodoItemsRepository
) {
    suspend operator fun invoke() = repo.refreshTodoItems()
}