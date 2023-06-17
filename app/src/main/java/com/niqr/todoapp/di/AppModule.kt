package com.niqr.todoapp.di

import com.niqr.todoapp.data.TodoItemsRepository
import com.niqr.todoapp.data.local.LocalTasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoItemsRepository(): TodoItemsRepository {
        return LocalTasksRepository()
    }
}