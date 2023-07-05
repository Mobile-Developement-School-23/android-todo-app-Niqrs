package com.niqr.tasks.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.niqr.tasks.data.model.TodoItemDto

@Database(entities = [TodoItemDto::class], version = 1)
abstract class TasksDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
