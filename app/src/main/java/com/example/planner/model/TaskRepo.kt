package com.example.planner.model

import androidx.annotation.WorkerThread
import com.example.planner.model.TaskDao
import com.example.planner.model.TaskItem
import kotlinx.coroutines.flow.Flow

class TaskRepo(private val taskDao: TaskDao)  {

    val allTasks: Flow<List<TaskItem>> = taskDao.allTasks()

    val undoneTasks: Flow<List<TaskItem>> = taskDao.undoneTasks()

    @WorkerThread
    suspend fun addTask(taskItem: TaskItem){
        taskDao.addTask(taskItem)
    }

    @WorkerThread
    suspend fun editTask(taskItem: TaskItem){
        taskDao.editTask(taskItem)
    }

    @WorkerThread
    suspend fun deleteTask(taskItem: TaskItem){
        taskDao.deleteTask(taskItem)
    }

    @WorkerThread
    suspend fun deleteAllTask(){
        taskDao.deleteAll()
    }
}