package com.niqr.other.work.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.work.ExistingWorkPolicy
import com.niqr.other.alarm.AlarmScheduler
import com.niqr.other.alarm.model.AlarmItem
import com.niqr.other.work.details.WorkDetails
import com.niqr.other.work.di.WorkComponentProvider
import com.niqr.other.work.utils.SYNCHRONIZATION_CHANNEL_ID
import com.niqr.other.work.utils.SYNCHRONIZATION_SERVICE_ID
import com.niqr.other.work.utils.SYNCHRONIZATION_WORK
import com.niqr.tasks.domain.repo.TodoItemsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject


/**
 * Provides data synchronization when app is closed
 */
class SynchronizationService: Service() {
    @Inject
    lateinit var details: WorkDetails
    @Inject
    lateinit var repo: TodoItemsRepository
    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    private val scope = CoroutineScope(Dispatchers.IO)
    private var syncJob: Job = Job()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        (applicationContext as WorkComponentProvider).provideWorkComponent().inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        trySync()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun trySync() {
        val notification = NotificationCompat.Builder(this, SYNCHRONIZATION_CHANNEL_ID).build()
        startForeground(SYNCHRONIZATION_SERVICE_ID, notification)
        syncJob.cancel()
        syncJob = scope.launch {
            if (!repo.refreshTodoItems())
                startSynchronizationWorker()

            val today = LocalDateTime.now()
            repo.todoItems()
                .first()
                .filter { !it.isDone && it.deadline?.let { deadline -> deadline > today } ?: false }
                .forEach {
                it.deadline?.let { deadline ->
                    alarmScheduler.schedule(AlarmItem(it.id), deadline)
                }
            }

            withContext(Dispatchers.Main) {
                stopSelf()
            }
        }
    }

    private fun startSynchronizationWorker() {
        details.workManager
            .beginUniqueWork(
                SYNCHRONIZATION_WORK,
                ExistingWorkPolicy.REPLACE,
                details.oneTimeSyncRequest
            )
            .enqueue()
    }
}
