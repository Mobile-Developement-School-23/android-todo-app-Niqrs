package com.niqr.todoapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.niqr.todoapp.utils.SYNCHRONIZATION_CHANNEL
import com.niqr.todoapp.utils.SYNCHRONIZATION_CHANNEL_ID
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TodoApplication: Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        val channel = NotificationChannel(
            SYNCHRONIZATION_CHANNEL_ID,
            SYNCHRONIZATION_CHANNEL,
            NotificationManager.IMPORTANCE_NONE
        )

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}