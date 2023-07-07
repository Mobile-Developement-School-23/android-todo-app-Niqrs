package com.niqr.other.work.workers

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.niqr.auth.domain.AuthInfoProvider
import com.niqr.other.work.R
import com.niqr.other.work.utils.SYNCHRONIZATION_CHANNEL_ID
import com.niqr.tasks.domain.repo.TodoItemsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlin.random.Random

class ScheduledSynchronizationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val repo: TodoItemsRepository,
    private val authProvider: AuthInfoProvider
): CoroutineWorker(context, params) {
    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            Random.nextInt(),
            NotificationCompat.Builder(context, SYNCHRONIZATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_sync_24dp)
                .setContentText(context.getString(R.string.synchronizing))
                .setContentTitle(context.getString(R.string.synchronization_in_progress))
                .build()
        )
    }

    override suspend fun doWork(): Result {
        if (authProvider.authInfo().token.isNotBlank() && !repo.refreshTodoItems())
            return Result.retry()
        return Result.success()
    }

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): SynchronizationWorker
    }
}
