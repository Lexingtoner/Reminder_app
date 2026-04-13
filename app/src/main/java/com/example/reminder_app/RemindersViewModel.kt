package com.example.reminder_app

import android.content.ContentValues
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class Reminder(val id: Int, val text: String, val date: String, val time: String)

class RemindersViewModel : ViewModel() {
    var text by mutableStateOf("")
    var date by mutableStateOf("")
    var time by mutableStateOf("")
    var reminders by mutableStateOf(listOf<Reminder>())

    fun addReminder(context: Context) {
        val db = DatabaseHelper(context).writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_TEXT, text)
            put(DatabaseHelper.COLUMN_DATE, date)
            put(DatabaseHelper.COLUMN_TIME, time)
        }
        db.insert(DatabaseHelper.TABLE_NAME, null, values)
        text = ""
        date = ""
        time = ""
        getReminders(context)
    }

    fun getReminders(context: Context) {
        val db = DatabaseHelper(context).readableDatabase
        val cursor = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null)
        val list = mutableListOf<Reminder>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
            val text = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEXT))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIME))
            list.add(Reminder(id, text, date, time))
        }
        cursor.close()
        reminders = list
    }

    fun deleteReminder(context: Context, id: Int) {
        val db = DatabaseHelper(context).writableDatabase
        db.delete(DatabaseHelper.TABLE_NAME, "${DatabaseHelper.COLUMN_ID} = ?", arrayOf(id.toString()))
        getReminders(context)
    }
}
