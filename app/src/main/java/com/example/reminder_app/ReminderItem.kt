package com.example.reminder_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReminderItem(reminder: Reminder, viewModel: RemindersViewModel, onUpdate: () -> Unit = {}) {
    val context = LocalContext.current

    val priorityColor = when (reminder.priority) {
        Priority.HIGH -> Color.Red
        Priority.NORMAL -> Color.Yellow
        Priority.LOW -> Color.Green
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.dark_navy)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Title with priority indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = reminder.text,
                    style = TextStyle(
                        color = colorResource(id = R.color.blue),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = if (reminder.completed) TextDecoration.LineThrough else TextDecoration.None
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = reminder.priority.name,
                    style = TextStyle(
                        color = priorityColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .background(
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            // Date, Time, and Category
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "${reminder.date} ${reminder.time}",
                    style = TextStyle(
                        color = colorResource(id = R.color.blue).copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                )
                Text(
                    text = reminder.category,
                    style = TextStyle(
                        color = colorResource(id = R.color.blue).copy(alpha = 0.7f),
                        fontSize = 12.sp
                    ),
                    modifier = Modifier
                        .background(
                            color = Color.Blue.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(3.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                )
            }

            // Action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.toggleCompleted(context, reminder.id) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (reminder.completed) "Undo" else "Done", fontSize = 12.sp)
                }
                OutlinedButton(
                    onClick = {
                        viewModel.editReminder(reminder)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Edit", fontSize = 12.sp)
                }
                Button(
                    onClick = { viewModel.deleteReminder(context, reminder.id) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Delete", fontSize = 12.sp)
                }
            }
        }
    }
}
