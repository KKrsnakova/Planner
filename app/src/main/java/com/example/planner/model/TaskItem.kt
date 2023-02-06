package com.example.planner.model

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.planner.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "table_task")
 class TaskItem(
    @ColumnInfo(name = "name")var name: String,
    @ColumnInfo(name = "desc")var desc: String,
    @ColumnInfo(name = "dueTime")var dueTimeString: String?,
    @ColumnInfo(name = "completedDate")var completedDateString: String?,
    @ColumnInfo(name = "date")var dateString: String?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
) {
    fun isCompleted() = completedDate() != null

    fun completedDate(): LocalDate? = if (completedDateString == null) null
    else LocalDate.parse(completedDateString, dateFormat)

    fun dueTime(): LocalTime? = if (dueTimeString == null) null
    else LocalTime.parse(dueTimeString, timeFormat)

    fun date(): LocalDate? = if (dateString == null) null
    else LocalDate.parse(dateString, dateFormat)


    fun imageResource(): Int = if (isCompleted()) R.drawable.checked_24 else R.drawable.unchecked_24
    fun imageColor(context: Context): Int = if (isCompleted()) pink(context) else black(context)


    private fun pink(context: Context) = ContextCompat.getColor(context, R.color.darkerPastelPink)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)


    companion object{
        val timeFormat: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        val dateFormat: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }



}