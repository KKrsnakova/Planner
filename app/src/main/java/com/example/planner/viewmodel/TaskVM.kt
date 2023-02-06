package com.example.planner

import androidx.lifecycle.*
import com.example.planner.model.TaskItem
import com.example.planner.model.TaskRepo
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskVM(private val repo: TaskRepo) : ViewModel() {


    var taskItems: LiveData<List<TaskItem>> = repo.allTasks.asLiveData()
    var undoneTasks: LiveData<List<TaskItem>> = repo.undoneTasks.asLiveData()




    fun addTaskItem(newTask: TaskItem) = viewModelScope.launch {
         repo.addTask(newTask)
    }

    fun editTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repo.editTask(taskItem)
    }

    fun deleteOnClick(taskItem: TaskItem) = viewModelScope.launch{
        repo.deleteTask(taskItem)
    }

    fun deleteAllOnClick() = viewModelScope.launch{
        repo.deleteAllTask()
    }

    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {
        if (!taskItem.isCompleted()){
            taskItem.completedDateString = TaskItem.dateFormat.format(LocalDate.now())
            repo.editTask(taskItem)
        } else {
            taskItem.completedDateString = null
            repo.editTask(taskItem)
        }

    }





  /*  fun editTaskItem(id: UUID, name: String, desc: String, dueTime: LocalTime?) {
        val list = taskItems.value
        val task = list!!.find { it.id == id }!!
        task.name = name
        task.desc = desc
        task.dueTime() = dueTime
        taskItems.postValue(list!!)
    }*/


//    fun setCompleded(taskItem: TaskItem) {
//        val list = taskItems.value
//        val task = list!!.find { it.id == taskItem.id }!!
//        if (task.completedDate == null) {
//            task.completedDate = LocalDate.now()
//        } else {
//            task.completedDate = null
//        }
//        taskItems.postValue(list!!)
//    }

/*
    fun updateTaskItem(
        id: UUID,
        name: String,
        desc: String,
        dueTime: LocalTime?,
        dateNew: LocalDate?
    ) {
        val list = taskItems.value
        val task = list!!.find { it.id == id }!!
        task.name = name
        task.desc = desc
        task.dueTime = dueTime
        task.date = dateNew
        taskItems.postValue(list!!)
    }
*/




    fun saveToFile() {

        /*val gson = Gson()
        val jsonList = mutableListOf<String>()

        taskItems.observe(this, Observer { taskItems ->
            for (taskItem in taskItems) {
                val jsonString = gson.toJson(taskItem)
                jsonList.add(jsonString)
            }
            val jsonArray = gson.toJson(jsonList)

            try {
                FileWriter("task_items.json").use { writer -> writer.write(jsonArray) }
            }catch (e: Exception) {
                e.printStackTrace()
            }

        })*/

    }

    fun saveData() {
        TODO("Not yet implemented")
    }




}

class TaskModelFactory(private val repo: TaskRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskVM::class.java)){
            return TaskVM(repo) as T
        } else {
            throw IllegalArgumentException("Chyba")
        }
    }
}