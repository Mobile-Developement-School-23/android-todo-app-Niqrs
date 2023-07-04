package com.niqr.tasks.domain.use_cases

import com.niqr.tasks.domain.repo.TodoItemsRepository

class FindTaskById(
    private val repo: TodoItemsRepository
) {
    suspend operator fun invoke(id: String) = repo.findItemById(id)
}