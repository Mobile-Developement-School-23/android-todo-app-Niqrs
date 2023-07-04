package com.niqr.tasks.data.di

import android.content.Context
import androidx.room.Room
import com.niqr.auth.domain.AuthInfoMutableProvider
import com.niqr.tasks.data.TasksRepositoryImpl
import com.niqr.tasks.data.local.db.TaskDao
import com.niqr.tasks.data.local.db.TasksDatabase
import com.niqr.tasks.data.remote.TasksService
import com.niqr.tasks.data.utils.DATABASE_NAME
import com.niqr.tasks.domain.repo.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TasksDataModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TasksDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TasksDatabase::class.java,
            name = DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(
        db: TasksDatabase
    ): TaskDao {
        return db.taskDao()
    }

    @Provides
    @Singleton
    fun provideTodoItemsRepository(
        service: TasksService,
        dao: TaskDao,
        authEditor: AuthInfoMutableProvider
    ): TodoItemsRepository {
        return TasksRepositoryImpl(authEditor, service, dao)
    }
}