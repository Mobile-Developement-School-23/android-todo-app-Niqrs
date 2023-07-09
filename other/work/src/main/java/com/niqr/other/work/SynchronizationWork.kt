package com.niqr.other.work

import android.content.Context
import android.content.Intent
import androidx.work.ExistingPeriodicWorkPolicy
import com.niqr.other.work.details.WorkDetails
import com.niqr.other.work.di.SynchronizationWorkScope
import com.niqr.other.work.services.SynchronizationService
import com.niqr.other.work.utils.PERIODIC_SYNCHRONIZATION_WORK
import javax.inject.Inject

/**
 * Encapsulate work of synchronization workers
 *
 * Starts synchronization workers and service
 */
@SynchronizationWorkScope
class SynchronizationWork @Inject constructor(
    private val context: Context,
    private val details: WorkDetails
) {
    fun beginOneTimeSynchronizationWork() {
        val app = context.applicationContext
        Intent(app, SynchronizationService::class.java).also {
            app.startService(it)
        }
    }

    fun enqueuePeriodicSynchronizationWork() {
        details.workManager.enqueueUniquePeriodicWork(
            PERIODIC_SYNCHRONIZATION_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            details.periodicSyncWork
        )
    }
}
