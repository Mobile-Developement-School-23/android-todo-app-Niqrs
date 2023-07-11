package com.niqr.other.work.details

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.niqr.other.work.di.SynchronizationWorkScope
import com.niqr.other.work.utils.PERIODIC_SYNCHRONIZATION_WORK_TAG
import com.niqr.other.work.utils.SYNCHRONIZATION_WORK_TAG
import com.niqr.other.work.workers.ScheduledSynchronizationWorker
import com.niqr.other.work.workers.SynchronizationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Store synchronization details
 */
@SynchronizationWorkScope
class WorkDetails @Inject constructor(
    context: Context
) {
    val workManager: WorkManager by lazy { WorkManager.getInstance(context) }

    private val constraints by lazy {
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    val oneTimeSyncRequest by lazy {
        OneTimeWorkRequestBuilder<SynchronizationWorker>()
            .addTag(SYNCHRONIZATION_WORK_TAG)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(constraints)
            .build()
    }

    val periodicSyncWork by lazy {
        PeriodicWorkRequestBuilder<ScheduledSynchronizationWorker>(
            8, TimeUnit.HOURS, 7, TimeUnit.HOURS)
            .addTag(PERIODIC_SYNCHRONIZATION_WORK_TAG)
            .setConstraints(constraints)
            .build()
    }
}
