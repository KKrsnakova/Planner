package com.example.planner.view

import android.app.Application
import com.example.planner.viewmodel.TaskDatabase
import com.example.planner.model.TaskRepo

class PlannerApl: Application()
{

    private val database by lazy { TaskDatabase.getDatabase(this) }
    val repo by lazy { TaskRepo(database.taskDao()) }

}