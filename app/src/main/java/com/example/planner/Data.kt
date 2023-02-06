package com.example.planner

import android.content.Context
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class Data {

    private val gson = Gson()

    fun loadTasks(context: Context): List<TaskItem> {
        val tasks: MutableList<TaskItem> = mutableListOf()
        val json = readFrom(context, "tasks.json")
        if (json != null) {
            val list: List<*> = gson.fromJson(json, Array<TaskItem>::class.java).toList()
            for (i in 0 until list.count()) {
                val value = list[i]
                if (value == null || value !is TaskItem)
                    continue
                tasks.add(value)
            }
        }
        return tasks
    }


    private fun readFrom(context: Context, fileName: String) : String? {
        val content = StringBuilder()
        try {
            val stream: FileInputStream = context.openFileInput(fileName)
            val bufferedReader = BufferedReader(InputStreamReader(stream))
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                content.append(line)
            }
        } catch (exception: Exception) {
            return null
        }
        return content.toString()
    }











}