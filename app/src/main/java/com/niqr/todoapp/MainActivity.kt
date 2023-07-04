package com.niqr.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.niqr.auth.domain.AuthInfoProvider
import com.niqr.core.ui.theme.TodoAppTheme
import com.niqr.other.work.SynchronizationWork
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authProvider: AuthInfoProvider
    private val syncWork by lazy { SynchronizationWork(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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