package com.niqr.todoapp.di

import android.content.Context
import androidx.room.Room
import com.niqr.todoapp.data.TodoItemsRepository
import com.niqr.todoapp.data.impl.TasksRepositoryImpl
import com.niqr.todoapp.data.local.TaskDao
import com.niqr.todoapp.data.local.TasksDatabase
import com.niqr.todoapp.data.remote.TasksService
import com.niqr.todoapp.utils.BASE_URL
import com.niqr.todoapp.utils.DATABASE_NAME
import com.niqr.todoapp.utils.LIST
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json()
            }
            defaultRequest {
                //header(GENERATE_FAILS, "10")
                contentType(ContentType.Application.Json)
                url(BASE_URL + LIST)
            }
        }
    }


    @Provides
    fun provideTodoItemsRepository(
        service: TasksService,
        dao: TaskDao
    ): TodoItemsRepository {
        return TasksRepositoryImpl(service, dao)
    }
}