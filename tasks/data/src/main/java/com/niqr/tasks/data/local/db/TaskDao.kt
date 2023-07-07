package com.niqr.tasks.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.niqr.tasks.data.model.TodoItemDto
import kotlinx.coroutines.flow.Flow

/**
 * Controls local tasks database
 *
 * Provides Flow of [TodoItemDto]
 */
@Dao
abstract class TaskDao {
    @Query("SELECT * FROM task")
    abstract fun getAllTasks(): Flow<List<TodoItemDto>>

    @Query("SELECT COUNT(*) FROM task WHERE done LIKE 1")
    abstract fun getDoneCount(): Flow<Int>

    @Query("SELECT * FROM task WHERE id LIKE :id LIMIT 1")
    abstract suspend fun findTaskById(id: String): TodoItemDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllTasks(tasks: List<TodoItemDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTask(task: TodoItemDto)

    @Update
    abstract suspend fun updateTasks(vararg: TodoItemDto)

    @Update
    abstract suspend fun updateTask(task: TodoItemDto)

    @Delete
    abstract suspend fun deleteTask(task: TodoItemDto)

    @Query("DELETE FROM task")
    abstract suspend fun clearAll()

    @Transaction
    open suspend fun replaceAll(tasks: List<TodoItemDto>) {
        clearAll()
        insertAllTasks(tasks)
    }
}
