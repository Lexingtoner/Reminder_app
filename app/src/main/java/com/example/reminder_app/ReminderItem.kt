package com.example.reminder_app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ReminderItem(reminder: Reminder, viewModel: RemindersViewModel) {
    val context = LocalContext.current
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(reminder.text)
            Text("${reminder.date} ${reminder.time}")
            Button(onClick = { viewModel.deleteReminder(context, reminder.id) }) {
                Text("Delete")
            }
        }
    }
}
