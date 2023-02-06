package com.example.planner

import android.app.Application

class PlannerApl: Application()
{

    private val database by lazy { TaskDatabase.getDatabase(this) }
    val repo by lazy { TaskRepo(database.taskDao()) }

}