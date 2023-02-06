package com.example.planner

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.planner.databinding.FragmentNewTaskBinding
import com.example.planner.model.TaskItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate
import java.time.LocalTime

class NewTask(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var taskManager: TaskVM
    private var dueTime: LocalTime? = null
    private var date: LocalDate? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()


        if (taskItem != null) {
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)

            if (taskItem!!.date() != null) {
                date = taskItem!!.date()!!
                updateDateBtmText()
            }

            if (taskItem!!.dueTime() != null) {
                dueTime = taskItem!!.dueTime()!!
                updateTimeBtmText()
            }

        } else {
            binding.taskTitle.text = "New Task"
        }

        taskManager = ViewModelProvider(activity).get(TaskVM::class.java)
        binding.btnSave.setOnClickListener {
            saveAction()
        }
        binding.btnTimePicker.setOnClickListener {
            openTimePicker()
        }

        binding.btnDatePicker.setOnClickListener {
            openDatePicker()
        }
    }

    private fun openDatePicker() {
        if (date == null) {
            date = LocalDate.now()
            Log.i("sss", "____openDatePicker:  " + date)
            var listener =
                DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                    date = LocalDate.of(selectedYear + 0, selectedMonth, selectedDay + 0)
                    updateDateBtmText()
                    Log.i("sss", "____update date:  " + date)
                }
            val datePicker = DatePickerDialog(
                requireActivity(),
                listener,
                date!!.year,
                date!!.monthValue + 1,
                date!!.dayOfMonth
            )
            datePicker.setTitle("Task Date")
            datePicker.show()
        } else {
            date = LocalDate.now()
            var listener =
                DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                    date = LocalDate.of(selectedYear + 0, selectedMonth + 1, selectedDay + 0)
                    updateDateBtmText()
                }
            val datePicker = DatePickerDialog(
                requireActivity(),
                listener,
                date!!.year,
                date!!.monthValue + 1,
                date!!.dayOfMonth
            )
            datePicker.setTitle("Task Date")
            datePicker.show()
        }
    }

    private fun openTimePicker() {
        if (dueTime == null) {
            dueTime = LocalTime.now()
            var listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                dueTime = LocalTime.of(selectedHour, selectedMinute)
                updateTimeBtmText()
            }
            val dialog =
                TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
            dialog.setTitle("Task time")
            dialog.show()
        } else {
            dueTime = LocalTime.now()
            var listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                dueTime = LocalTime.of(selectedHour, selectedMinute)
                updateTimeBtmText()
            }
            val dialog =
                TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
            dialog.setTitle("Task time")
            dialog.show()
        }
    }

    private fun updateTimeBtmText() {
        binding.btnTimePicker.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }

    private fun updateDateBtmText() {
        //  binding.btnDatePicker.text = String.format("%02d %02d %0002d", date!!.dayOfWeek,date!!.monthValue, date!!.year)
        binding.btnDatePicker.text = date.toString()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction() {

        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val dueTimeString = if (dueTime == null) null else TaskItem.timeFormat.format(dueTime)
        val dateString = if (date == null) null else TaskItem.dateFormat.format(date)

        if (taskItem == null) {
            if (name != null && dueTimeString !=null && dateString!= null ){
                val newTask = TaskItem(name, desc, dueTimeString, null, dateString)
                taskManager.addTaskItem(newTask)
            } else {
                Toast.makeText(activity, "Not saved! Name, date and time must be filled  ", Toast.LENGTH_LONG).show()
            }


        } else {
            taskItem!!.name = name
            taskItem!!.desc = desc
            taskItem!!.dueTimeString = dueTimeString
            taskItem!!.dateString = dateString
            taskManager.editTaskItem(taskItem!!)
        }
        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }




}