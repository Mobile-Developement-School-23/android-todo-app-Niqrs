package com.niqr.tasks.ui.di

import com.niqr.core.di.FeatureScope
import com.niqr.tasks.ui.TasksViewModel
import dagger.Subcomponent

/**
 * Dagger component for tasks ui layer
 */
@Subcomponent
@FeatureScope
interface TasksUiComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TasksUiComponent
    }

    fun getViewModel(): TasksViewModel
}

interface TasksUiComponentProvider {
    fun provideTasksUiComponent(): TasksUiComponent
}
