package com.niqr.tasks.ui.di

import com.niqr.auth.domain.AuthRepository
import com.niqr.core.di.FeatureScope
import com.niqr.tasks.domain.repo.TodoItemsRepository
import com.niqr.tasks.ui.TasksViewModel
import dagger.Module
import dagger.Provides

@Module
class TasksViewModelModule {

    @Provides
    @FeatureScope
    fun provideViewModel(
        authRepo: AuthRepository,
        tasksRepo: TodoItemsRepository
    ): TasksViewModel = TasksViewModel(authRepo, tasksRepo)
}