package com.example.reminder_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getIntExtra("reminder_id", -1)
        val reminderText = intent.getStringExtra("reminder_text") ?: "Reminder"
        val reminderCategory = intent.getStringExtra("reminder_category") ?: "General"

        if (reminderId != -1) {
            NotificationScheduler.showNotification(context, reminderId, reminderText, reminderCategory)
        }
    }
}

