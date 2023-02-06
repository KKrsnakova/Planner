package com.example.planner.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.planner.model.TaskItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM table_task ORDER BY id ASC")
    fun allTasks(): Flow<List<TaskItem>>

    @Query("SELECT * FROM table_task WHERE completedDate = null ORDER BY id ASC")
    fun undoneTasks(): Flow<List<TaskItem>>

    @Query("DELETE FROM table_task")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(taskItem: TaskItem)
    @Update
    suspend fun editTask(taskItem: TaskItem)
    @Delete
    suspend fun deleteTask(taskItem: TaskItem)


}