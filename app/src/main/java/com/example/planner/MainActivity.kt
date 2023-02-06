package com.example.planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.planner.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type

class MainActivity : AppCompatActivity(), TaskItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private var all: Boolean = true
    private val tasks: TaskVM by viewModels {

        TaskModelFactory((application as PlannerApl).repo)
    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnNewTask.setOnClickListener {
            NewTask(null).show(supportFragmentManager, "newTask")
        }

        binding.btnDeleteAll.setOnClickListener {
            tasks.deleteAllOnClick()
        }

        setRecyclerView()
    }

    /*fun readSettingsJson(): String {
        var string: String? = ""
        val stringBuilder = StringBuilder()
        val inputStream: InputStream = this.resources.openRawResource(R.raw.tasks)
        val reader = BufferedReader(InputStreamReader(inputStream))
        while (true) {
            try {
                if (reader.readLine().also { string = it } == null) break
            } catch (e: IOException) {
                e.printStackTrace()
            }
            stringBuilder.append(string).append("\n")

        }
        inputStream.close()
        return stringBuilder.toString()
    }


    private fun saveData() {
        val sharedPreferences = getSharedPreferences("preferenc", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        var json = gson.toJson(tasks)
        editor.putString("task list", json)
        Log.i("_______2", "loadData: " + json)
        editor.apply()


    }
*/


    private fun setRecyclerView() {
        val mainActivity = this
        tasks.taskItems.observe(this) {

            binding.RecyclerViewPlanner.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }
    }


    override fun editTaskItem(taskItem: TaskItem) {
        //
        val choices = arrayOf("Edit", "Delete")
        var builder = AlertDialog.Builder(this)

        builder.setSingleChoiceItems(choices, -1) { dialogInterface, i ->
            val selectd = choices[i]
            if (selectd.equals("Edit")) {

                NewTask(taskItem).show(supportFragmentManager, "newTaskTag")
                dialogInterface.dismiss()
            } else {
                tasks.deleteOnClick(taskItem)
                dialogInterface.dismiss()
            }

        }
        builder.setNeutralButton("Cancle") { dialog, witch ->
            dialog.cancel()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        tasks.setCompleted(taskItem)
    }



}





