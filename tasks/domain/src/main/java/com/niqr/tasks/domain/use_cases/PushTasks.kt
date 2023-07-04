package com.niqr.tasks.domain.use_cases

import com.niqr.tasks.domain.repo.TodoItemsRepository

class PushTasks(
    private val repo: TodoItemsRepository
) {
    suspend operator fun invoke() = repo.pushTodoItems()
}