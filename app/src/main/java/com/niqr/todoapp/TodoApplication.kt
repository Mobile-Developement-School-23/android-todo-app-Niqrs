package com.niqr.todoapp

import android.app.Application
import android.app.NotificationManager
import com.niqr.auth.ui.di.AuthUiComponent
import com.niqr.auth.ui.di.AuthUiComponentProvider
import com.niqr.edit.ui.di.EditUiComponent
import com.niqr.edit.ui.di.EditUiComponentProvider
import com.niqr.other.work.SynchronizationNotificationChannel
import com.niqr.tasks.ui.di.TasksUiComponent
import com.niqr.tasks.ui.di.TasksUiComponentProvider
import com.niqr.todoapp.di.AppComponent
import com.niqr.todoapp.di.DaggerAppComponent

/**
 * Custom Application class allows to hold reference to [appComponent]
 * as long as application lives.
 *
 * Provides app features dagger components
 *
 * Creates [SynchronizationNotificationChannel]
 */
class TodoApplication: Application(),
    AuthUiComponentProvider,
    TasksUiComponentProvider,
    EditUiComponentProvider {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        val syncChannel = SynchronizationNotificationChannel()

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(syncChannel.channel)
    }

    override fun provideAuthUiComponent(): AuthUiComponent =
        appComponent.authUiComponent().create()
    override fun provideTasksUiComponent(): TasksUiComponent =
        appComponent.tasksUiComponent().create()
    override fun provideEditUiComponent(): EditUiComponent =
        appComponent.editUiComponent().create()
}
