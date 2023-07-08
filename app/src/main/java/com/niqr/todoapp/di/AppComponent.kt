package com.niqr.todoapp.di

import android.content.Context
import com.niqr.auth.data.di.AuthProviderScope
import com.niqr.auth.ui.di.AuthUiComponent
import com.niqr.core.di.AppScope
import com.niqr.edit.ui.di.EditUiComponent
import com.niqr.other.work.di.SynchronizationWorkScope
import com.niqr.tasks.ui.di.TasksUiComponent
import com.niqr.todoapp.MainActivity
import com.niqr.todoapp.TodoApplication
import dagger.BindsInstance
import dagger.Component

/**
 * Root of Dagger DI graph
 */
@AppScope
@AuthProviderScope
@SynchronizationWorkScope
@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }

    fun inject(app: TodoApplication)
    fun inject(activity: MainActivity)

    fun authUiComponent(): AuthUiComponent.Factory
    fun tasksUiComponent(): TasksUiComponent.Factory
    fun editUiComponent(): EditUiComponent.Factory
}
