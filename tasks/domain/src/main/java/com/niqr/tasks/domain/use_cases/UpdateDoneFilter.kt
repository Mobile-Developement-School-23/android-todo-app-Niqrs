package com.niqr.tasks.domain.use_cases

import com.niqr.tasks.domain.repo.TodoItemsRepository

class UpdateDoneFilter(
    private val repo: TodoItemsRepository
) {
    suspend operator fun invoke(filter: Boolean) = repo.updateDoneTodoItemsVisibility(!filter)
}