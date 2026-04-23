package com.example.reminder_app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun List(viewModel: RemindersViewModel = viewModel(), modifier: Modifier = Modifier) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getReminders(context)
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Search bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                label = { Text("Search") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorResource(id = R.color.navy),
                    unfocusedContainerColor = colorResource(id = R.color.navy),
                    focusedTextColor = colorResource(id = R.color.blue),
                    unfocusedTextColor = colorResource(id = R.color.blue)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Button(onClick = { viewModel.toggleShowCompleted() }) {
                Text(if (viewModel.showCompletedOnly) "Completed" else "All")
            }
        }

        // Reminders list
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(viewModel.filteredReminders) { _, reminder ->
                ReminderItem(reminder, viewModel, { viewModel.getReminders(context) })
            }
        }
    }
}