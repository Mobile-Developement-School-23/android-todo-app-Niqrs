package com.niqr.other.work.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.niqr.other.work.workers.ScheduledSynchronizationWorker
import com.niqr.other.work.workers.SynchronizationWorker
import javax.inject.Inject

/**
 * Create synchronization workers
 */
class SynchronizationWorkerFactory @Inject constructor(
    private val synchronizationWorkerFactory: SynchronizationWorker.Factory,
    private val scheduledSynchronizationWorkerFactory: ScheduledSynchronizationWorker.Factory
): WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SynchronizationWorker::class.java.name ->
                synchronizationWorkerFactory.create(appContext, workerParameters)
            ScheduledSynchronizationWorker::class.java.name ->
                scheduledSynchronizationWorkerFactory.create(appContext, workerParameters)
            else -> null
        }
    }
}
