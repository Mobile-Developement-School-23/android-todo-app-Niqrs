package com.niqr.edit.ui.di

import com.niqr.core.di.FeatureScope
import com.niqr.edit.ui.TaskEditViewModel
import com.niqr.tasks.domain.repo.TodoItemsRepository
import dagger.Module
import dagger.Provides

@Module
class TaskEditViewModelModule {

    @Provides
    @FeatureScope
    fun provideViewModel(
        tasksRepo: TodoItemsRepository
    ): TaskEditViewModel = TaskEditViewModel(tasksRepo)
}