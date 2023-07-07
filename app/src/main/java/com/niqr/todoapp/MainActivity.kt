package com.niqr.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.niqr.auth.domain.AuthInfoProvider
import com.niqr.core.ui.theme.TodoAppTheme
import com.niqr.other.work.SynchronizationWork
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as TodoApplication).appComponent.inject(this)
        syncWork.enqueuePeriodicSynchronizationWork()
        setContent {
            TodoAppTheme {
                TodoNavigation(authProvider)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        syncWork.beginOneTimeSynchronizationWork()
    }
}