package com.example.reminder_app

import android.content.ContentValues
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class Priority {
    HIGH, NORMAL, LOW
}

data class Reminder(
    val id: Int,
    val text: String,
    val date: String,
    val time: String,
    val priority: Priority = Priority.NORMAL,
    val completed: Boolean = false,
    val category: String = "Other"
)

class RemindersViewModel : ViewModel() {
    // Form fields
    var text by mutableStateOf("")
    var date by mutableStateOf("")
    var time by mutableStateOf("")
    var priority by mutableStateOf(Priority.NORMAL)
    var category by mutableStateOf("Other")
    var editingReminderId by mutableStateOf<Int?>(null)

    // List fields
    var reminders by mutableStateOf(listOf<Reminder>())
    var filteredReminders by mutableStateOf(listOf<Reminder>())
    var searchQuery by mutableStateOf("")
    var showCompletedOnly by mutableStateOf(false)

    fun addReminder(context: Context) {
        if (text.isBlank() || date.isBlank() || time.isBlank()) return

        val db = DatabaseHelper(context).writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_TEXT, text)
            put(DatabaseHelper.COLUMN_DATE, date)
            put(DatabaseHelper.COLUMN_TIME, time)
            put(DatabaseHelper.COLUMN_PRIORITY, priority.name)
            put(DatabaseHelper.COLUMN_CATEGORY, category)
            put(DatabaseHelper.COLUMN_COMPLETED, 0)
        }

        if (editingReminderId != null) {
            db.update(DatabaseHelper.TABLE_NAME, values, "${DatabaseHelper.COLUMN_ID} = ?", arrayOf(editingReminderId.toString()))
            editingReminderId = null
        } else {
            db.insert(DatabaseHelper.TABLE_NAME, null, values)
        }

        clearForm()
        getReminders(context)
    }

    fun getReminders(context: Context) {
        val db = DatabaseHelper(context).readableDatabase
        val cursor = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, "${DatabaseHelper.COLUMN_DATE} ASC, ${DatabaseHelper.COLUMN_TIME} ASC")
        val list = mutableListOf<Reminder>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
            val text = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEXT))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIME))
            val priority = Priority.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRIORITY)))
            val completed = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COMPLETED)) == 1
            val category = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY))
            list.add(Reminder(id, text, date, time, priority, completed, category))
        }
        cursor.close()
        reminders = list
        updateFilteredReminders()
    }

    fun deleteReminder(context: Context, id: Int) {
        val db = DatabaseHelper(context).writableDatabase
        db.delete(DatabaseHelper.TABLE_NAME, "${DatabaseHelper.COLUMN_ID} = ?", arrayOf(id.toString()))
        NotificationScheduler.cancelNotification(context, id)
        getReminders(context)
    }

    fun toggleCompleted(context: Context, id: Int) {
        val reminder = reminders.find { it.id == id } ?: return
        val db = DatabaseHelper(context).writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_COMPLETED, if (reminder.completed) 0 else 1)
        }
        db.update(DatabaseHelper.TABLE_NAME, values, "${DatabaseHelper.COLUMN_ID} = ?", arrayOf(id.toString()))
        getReminders(context)
    }

    fun editReminder(reminder: Reminder) {
        text = reminder.text
        date = reminder.date
        time = reminder.time
        priority = reminder.priority
        category = reminder.category
        editingReminderId = reminder.id
    }

    fun clearForm() {
        text = ""
        date = ""
        time = ""
        priority = Priority.NORMAL
        category = "Other"
        editingReminderId = null
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
        updateFilteredReminders()
    }

    fun toggleShowCompleted() {
        showCompletedOnly = !showCompletedOnly
        updateFilteredReminders()
    }

    private fun updateFilteredReminders() {
        var filtered = reminders

        if (searchQuery.isNotBlank()) {
            filtered = filtered.filter { it.text.contains(searchQuery, ignoreCase = true) || it.category.contains(searchQuery, ignoreCase = true) }
        }

        if (!showCompletedOnly) {
            filtered = filtered.filter { !it.completed }
        }

        filteredReminders = filtered
    }
}
