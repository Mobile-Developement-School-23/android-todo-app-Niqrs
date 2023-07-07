package com.niqr.tasks.data.di

import android.content.Context
import androidx.room.Room
import com.niqr.core.di.AppScope
import com.niqr.tasks.data.TasksRepositoryImpl
import com.niqr.tasks.data.local.db.TaskDao
import com.niqr.tasks.data.local.db.TasksDatabase
import com.niqr.tasks.data.utils.DATABASE_NAME
import com.niqr.tasks.domain.repo.TodoItemsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [TasksDataBindingModule::class])
class TasksDataModule {
    @Provides
    @AppScope
    fun provideDatabase(
        context: Context
    ): TasksDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TasksDatabase::class.java,
            name = DATABASE_NAME
        ).build()
    }

    @Provides
    @AppScope
    fun provideTaskDao(
        db: TasksDatabase
    ): TaskDao {
        return db.taskDao()
    }
}

@Module
interface TasksDataBindingModule {
    @Binds
    @AppScope
    fun provideTodoItemsRepository(
        repo: TasksRepositoryImpl
    ): TodoItemsRepository
}
