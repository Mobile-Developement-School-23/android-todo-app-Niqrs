package com.niqr.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.niqr.auth.domain.AuthInfoProvider
import com.niqr.core.ui.theme.TodoAppTheme
import com.niqr.other.work.SynchronizationWork
import com.niqr.settings.domain.settings.AppSettingsProvider
import com.niqr.todoapp.utils.PermissionsChecker
import com.niqr.todoapp.utils.isDarkTheme
import javax.inject.Inject

/**
 * Entry point for app UI
 *
 * Starts synchronization work with [SynchronizationWork]
 */
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authProvider: AuthInfoProvider
    @Inject
    lateinit var syncWork: SynchronizationWork
    @Inject
    lateinit var settings: AppSettingsProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as TodoApplication).appComponent.inject(this)
        syncWork.enqueuePeriodicSynchronizationWork()
        setContent {
            val settings by settings.settingsFlow().collectAsState(settings.settings())
            PermissionsChecker()
            TodoAppTheme(
                darkTheme = settings.theme.isDarkTheme()
            ) {
                TodoNavigation(authProvider)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        syncWork.beginOneTimeSynchronizationWork()
    }
}