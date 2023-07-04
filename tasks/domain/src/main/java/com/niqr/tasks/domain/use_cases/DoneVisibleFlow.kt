package com.niqr.tasks.domain.use_cases

import com.niqr.tasks.domain.repo.TodoItemsRepository

class DoneVisibleFlow(
    private val repo: TodoItemsRepository
) {
    operator fun invoke() = repo.doneVisible()
}