package com.example.reminder_app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun List(viewModel: RemindersViewModel = viewModel(), modifier: Modifier = Modifier) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getReminders(context)
    }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        itemsIndexed(viewModel.reminders) {
                _, reminder -> ReminderItem(reminder, viewModel)
        }
    }
}