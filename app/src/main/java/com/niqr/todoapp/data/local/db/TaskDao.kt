package com.niqr.todoapp.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.niqr.todoapp.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TaskDao {
    @Query("SELECT * FROM task")
    abstract fun getAllTasks(): Flow<List<TodoItem>>

    @Query("SELECT * FROM task WHERE done LIKE 0")
    abstract fun getAllUndoneTasks(): Flow<List<TodoItem>>

    @Query("SELECT COUNT(*) FROM task WHERE done LIKE 1")
    abstract fun getDoneCount(): Flow<Int>

    @Query("SELECT * FROM task WHERE id LIKE :id LIMIT 1")
    abstract suspend fun findTaskById(id: String): TodoItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllTasks(tasks: List<TodoItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTask(task: TodoItem)

    @Update
    abstract suspend fun updateTasks(vararg: TodoItem)

    @Update
    abstract suspend fun updateTask(task: TodoItem)

    @Delete
    abstract suspend fun deleteTask(task: TodoItem)

    @Query("DELETE FROM task")
    abstract suspend fun clearAll()

    @Transaction
    open suspend fun replaceAll(tasks: List<TodoItem>) {
        clearAll()
        insertAllTasks(tasks)
    }
}