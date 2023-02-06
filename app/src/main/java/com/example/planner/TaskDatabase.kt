package com.example.planner

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskItem::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object
    {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        //vráti databázi
        //databaze je inicializovaná - vráti DB
        //Pokud ne, inicializuje se instance databáze
        fun getDatabase(context: Context): TaskDatabase
        {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}