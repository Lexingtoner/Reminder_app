package com.example.reminder_app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Form(viewModel: RemindersViewModel = viewModel(), onReminderAdded: () -> Unit = {}) {
    val context = LocalContext.current
    val isEditing = viewModel.editingReminderId != null

    Column(
        modifier = Modifier
            .padding(10.dp)
            .background(colorResource(id = R.color.dark_navy), RoundedCornerShape(15.dp))
            .border(0.5.dp, colorResource(id = R.color.blue), RoundedCornerShape(15.dp))
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = if (isEditing) "Edit Reminder" else stringResource(id = R.string.form_title),
            style = TextStyle(
                color = colorResource(id = R.color.white),
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Thin
            )
        )

        ReminderTextField(viewModel)
        PrioritySelector(viewModel)
        CategorySelector(viewModel)
        DateTimeInputFields(viewModel)
        FormButtons(
            isEditing = isEditing,
            onSave = {
                viewModel.addReminder(context)
                onReminderAdded()
            },
            onCancel = {
                viewModel.clearForm()
                onReminderAdded()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderTextField(viewModel: RemindersViewModel) {
    TextField(
        value = viewModel.text,
        onValueChange = { viewModel.text = it },
        label = { Text(text = stringResource(id = R.string.form_text_hint)) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedTextColor = colorResource(id = R.color.blue),
            unfocusedTextColor = colorResource(id = R.color.blue),
            cursorColor = colorResource(id = R.color.blue),
            focusedLabelColor = colorResource(id = R.color.blue),
            unfocusedLabelColor = colorResource(id = R.color.blue),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 8.dp)
    )
}

@Composable
fun PrioritySelector(viewModel: RemindersViewModel) {
    val expanded = remember { mutableStateOf(false) }
    val priorities = listOf(Priority.HIGH, Priority.NORMAL, Priority.LOW)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 8.dp)
    ) {
        Text("Priority", color = colorResource(id = R.color.blue), fontSize = 12.sp)
        Box {
            Button(
                onClick = { expanded.value = !expanded.value },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.navy)
                )
            ) {
                Text(viewModel.priority.name, color = colorResource(id = R.color.blue))
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                priorities.forEach { priority ->
                    DropdownMenuItem(
                        text = { Text(priority.name) },
                        onClick = {
                            viewModel.priority = priority
                            expanded.value = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CategorySelector(viewModel: RemindersViewModel) {
    val expanded = remember { mutableStateOf(false) }
    val categories = listOf("Work", "Personal", "Shopping", "Health", "Other")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 8.dp)
    ) {
        Text("Category", color = colorResource(id = R.color.blue), fontSize = 12.sp)
        Box {
            Button(
                onClick = { expanded.value = !expanded.value },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.navy)
                )
            ) {
                Text(viewModel.category, color = colorResource(id = R.color.blue))
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            viewModel.category = category
                            expanded.value = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DateTimeInputFields(viewModel: RemindersViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DateInputField(viewModel)
        TimeInputField(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInputField(viewModel: RemindersViewModel) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context,
        {
                _: DatePicker,
                selectedYear: Int,
                selectedMonth: Int,
                selectedDay: Int -> viewModel.date = "${Utils.addZero(selectedDay)}.${Utils.addZero(selectedMonth + 1)}.$selectedYear"},
        year, month, day
    )

    Box {
        TextField(
            value = viewModel.date,
            onValueChange = { viewModel.date = it },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = colorResource(id = R.color.navy),
                focusedContainerColor = colorResource(id = R.color.navy),
                unfocusedContainerColor = colorResource(id = R.color.navy)
            ),
            enabled = false
        )
        Text(
            text = if(viewModel.date.isNotEmpty()) viewModel.date else stringResource(id = R.string.form_date_hint),
            color = colorResource(id = R.color.blue),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInputField(viewModel: RemindersViewModel) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val timePickerDialog = TimePickerDialog(
        context,
        {
                _: TimePicker,
                selectedHour: Int,
                selectedMinute: Int -> viewModel.time = "${Utils.addZero(selectedHour)}:${Utils.addZero(selectedMinute)}"},
        hour, minute, true
    )

    Box {
        TextField(
            value = viewModel.time,
            onValueChange = { viewModel.time = it },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { timePickerDialog.show() },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = colorResource(id = R.color.navy),
                focusedContainerColor = colorResource(id = R.color.navy),
                unfocusedContainerColor = colorResource(id = R.color.navy)
            ),
            enabled = false
        )
        Text(
            text = if(viewModel.time.isNotEmpty()) viewModel.time else stringResource(id = R.string.form_time_hint),
            color = colorResource(id = R.color.blue),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
        )
    }
}

@Composable
fun FormButtons(isEditing: Boolean, onSave: () -> Unit, onCancel: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isEditing) {
            OutlinedButton(
                onClick = {
                    onCancel()
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .weight(1f)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                colorResource(id = R.color.navy),
                                colorResource(id = R.color.navy)
                            )
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
            ) {
                Text("Cancel", color = colorResource(id = R.color.blue))
            }
        }

        Button(
            onClick = {
                onSave()
                keyboardController?.hide()
            },
            modifier = Modifier
                .weight(1f)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            colorResource(id = R.color.button_gradient_purple),
                            colorResource(id = R.color.button_gradient_blue)
                        )
                    ),
                    shape = RoundedCornerShape(15.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Text(
                if (isEditing) "Update" else stringResource(id = R.string.form_create),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}