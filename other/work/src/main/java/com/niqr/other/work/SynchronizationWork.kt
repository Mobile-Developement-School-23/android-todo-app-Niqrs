package com.niqr.other.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.niqr.other.work.utils.PERIODIC_SYNCHRONIZATION_WORK
import com.niqr.other.work.utils.PERIODIC_SYNCHRONIZATION_WORK_TAG
import com.niqr.other.work.utils.SYNCHRONIZATION_WORK
import com.niqr.other.work.utils.SYNCHRONIZATION_WORK_TAG
import com.niqr.other.work.workers.ScheduledSynchronizationWorker
import com.niqr.other.work.workers.SynchronizationWorker
import java.util.concurrent.TimeUnit

class SynchronizationWork(
    context: Context
) {
    private val workManager: WorkManager

    init {
        workManager = WorkManager.getInstance(context)
    }

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private val oneTimeSyncRequest by lazy {
        OneTimeWorkRequestBuilder<SynchronizationWorker>()
            .addTag(SYNCHRONIZATION_WORK_TAG)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(constraints)
            .build()
    }

    private val periodicSyncWork by lazy {
        PeriodicWorkRequestBuilder<ScheduledSynchronizationWorker>(
            8, TimeUnit.HOURS, 7, TimeUnit.HOURS)
            .addTag(PERIODIC_SYNCHRONIZATION_WORK_TAG)
            .setConstraints(constraints)
            .build()
    }

    fun beginOneTimeSynchronizationWork() {
        workManager
            .beginUniqueWork(
                SYNCHRONIZATION_WORK,
                ExistingWorkPolicy.REPLACE,
                oneTimeSyncRequest
            )
            .enqueue()
    }

    fun enqueuePeriodicSynchronizationWork() {
        workManager.enqueueUniquePeriodicWork(
            PERIODIC_SYNCHRONIZATION_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncWork
        )
    }
}