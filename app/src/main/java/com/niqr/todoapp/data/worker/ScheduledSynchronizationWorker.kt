package com.niqr.todoapp.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.niqr.todoapp.data.abstraction.AuthInfoProvider
import com.niqr.todoapp.data.abstraction.TodoItemsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ScheduledSynchronizationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val repo: TodoItemsRepository,
    private val authProvider: AuthInfoProvider
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (authProvider.authInfo().token.isNotBlank() && !repo.refreshTodoItems())
            return Result.retry()
        return Result.success()
    }
}