package com.niqr.todoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.niqr.todoapp.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<TodoItem>>

    @Query("SELECT * FROM task WHERE done LIKE 0")
    fun getAllUndoneTasks(): Flow<List<TodoItem>>

    @Query("SELECT COUNT(*) FROM task WHERE done LIKE 1")
    fun getDoneCount(): Flow<Int>

    @Query("SELECT * FROM task WHERE id LIKE :id LIMIT 1")
    suspend fun findTaskById(id: String): TodoItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTasks(vararg tasks: TodoItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TodoItem)

    @Update
    suspend fun updateTasks(vararg: TodoItem)

    @Update
    suspend fun updateTask(task: TodoItem)

    @Delete
    suspend fun deleteTask(task: TodoItem)
}