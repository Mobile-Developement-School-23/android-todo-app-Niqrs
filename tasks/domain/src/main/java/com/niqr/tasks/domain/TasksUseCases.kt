package com.niqr.tasks.domain

import com.niqr.tasks.domain.use_cases.AddTask
import com.niqr.tasks.domain.use_cases.ClearTasks
import com.niqr.tasks.domain.use_cases.DeleteTask
import com.niqr.tasks.domain.use_cases.DoneCountFlow
import com.niqr.tasks.domain.use_cases.DoneVisibleFlow
import com.niqr.tasks.domain.use_cases.FindTaskById
import com.niqr.tasks.domain.use_cases.PushTasks
import com.niqr.tasks.domain.use_cases.RefreshTasks
import com.niqr.tasks.domain.use_cases.TodoItems
import com.niqr.tasks.domain.use_cases.UpdateDoneFilter
import com.niqr.tasks.domain.use_cases.UpdateTask
import com.niqr.tasks.domain.use_cases.UpdateTasks

data class TasksUseCases(
    val tasksFlow: TodoItems,
    val doneVisibleFlow: DoneVisibleFlow,
    val doveCountFlow: DoneCountFlow,
    val findTaskById: FindTaskById,
    val addTask: AddTask,
    val updateTask: UpdateTask,
    val deleteTask: DeleteTask,
    val updateDoneFilter: UpdateDoneFilter,
    val updateTasks: UpdateTasks,
    val refreshTasks: RefreshTasks,
    val pushTasks: PushTasks,
    val clearTasks: ClearTasks
)