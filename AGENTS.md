# AGENTS.md for Reminder_app

## Architecture Overview
This is an Android reminder app built with Kotlin and Jetpack Compose. It uses SQLite for local data storage and follows MVVM pattern with ViewModels. 

**Latest Updates:** Added priority levels, categories, search/filtering, edit mode, completion status, and notification scheduling.

Key components:
- **DatabaseHelper**: Manages SQLite database with `reminders` table (id, text, date, time, priority, completed, category)
- **RemindersViewModel**: State management with search/filter functionality, edit mode support
- **Form**: Compose screen for adding/editing reminders with date/time pickers, priority selector, category dropdown
- **List**: Compose screen displaying reminders in LazyColumn with search bar and filter toggles
- **ReminderItem**: Card-based item rendering with priority color coding, completion toggle, edit/delete buttons
- **MainActivity**: Entry point with tabbed navigation (List ↔ Form)
- **NotificationScheduler**: AlarmManager-based reminder notifications
- **ReminderNotificationReceiver**: BroadcastReceiver for handling notifications

Data flows from Form → ViewModel → DatabaseHelper for storage, and List ← ViewModel ← DatabaseHelper for retrieval. Search/filter runs client-side in ViewModel.

## Key Files & Patterns
- `app/build.gradle.kts`: Uses version catalog (`gradle/libs.versions.toml`) for dependencies. Compose enabled via `buildFeatures { compose = true }`
- `DatabaseHelper.kt`: SQLiteOpenHelper subclass managing v1→v2 schema migration. Table: `CREATE TABLE reminders(id INTEGER PRIMARY KEY, text TEXT, date TEXT, time TEXT, priority TEXT DEFAULT 'normal', completed INTEGER DEFAULT 0, category TEXT DEFAULT 'Other')`
- `Form.kt`: Composable functions for ReminderTextField, DateInputField, TimeInputField, PrioritySelector (dropdown), CategorySelector (dropdown). Uses DatePickerDialog/TimePickerDialog. Form title changes to "Edit Reminder" when editing.
- `List.kt`: Displays filteredReminders (not all reminders). Has SearchTextField and button to toggle completed items display. Calls viewModel.updateSearchQuery() and viewModel.toggleShowCompleted()
- `ReminderItem.kt`: Card with reminder text, date/time, priority color badge, category tag, and three buttons (Done/Undo, Edit, Delete)
- `RemindersViewModel.kt`: Maintains `reminders` (all), `filteredReminders` (search-filtered), `searchQuery`, `showCompletedOnly`, `editingReminderId`. Methods: addReminder(context), editReminder(reminder), deleteReminder(context, id), toggleCompleted(context, id), updateSearchQuery(query), toggleShowCompleted()
- `NotificationScheduler.kt`: Static object with scheduleNotification(), cancelNotification(), showNotification() using AlarmManager (exact for API 31+, standard for older)
- `ReminderNotificationReceiver.kt`: BroadcastReceiver calling NotificationScheduler.showNotification()

## Conventions
- Package: `com.example.reminder_app`
- UI: Jetpack Compose with Material3. Custom colors as `colorResource(id = R.color.*)` (dark_navy, blue, navy, button_gradient_purple, button_gradient_blue)
- Strings: Localized via `stringResource(id = R.string.*)` (form_title, form_text_hint, form_date_hint, form_time_hint, form_create, priority_high, priority_normal, priority_low)
- Date/Time: ThreeTenABP backport. Format: "DD.MM.YYYY" and "HH:MM" with leading zeros via `Utils.addZero()`
- ViewModel: Shared RemindersViewModel instance. State via `mutableStateOf()`
- Database sorting: `ORDER BY date ASC, time ASC` (chronological)
- Enums: Priority enum (HIGH, NORMAL, LOW) used for color coding in UI
- Categories: Predefined list (Work, Personal, Shopping, Health, Other) in CategorySelector dropdown

## Workflows
- **Build**: `./gradlew.bat build` (Windows) or `./gradlew build` (Linux/Mac) from project root
- **Run/Debug**: `./gradlew.bat assembleDebug` then install APK or use Android Studio Run
- **Database Migration**: onUpgrade() handles v1→v2 via ALTER TABLE to avoid data loss. Called when DATABASE_VERSION increments.
- **Add Reminder**: Form validates (text, date, time non-blank) → insert into DB → schedule notification → clear form → refresh list
- **Edit Reminder**: Tap Edit button → form loads data (editingReminderId set, form title changes) → user modifies → Update button does UPDATE query → reschedule notification
- **Search**: Type in search bar → calls updateSearchQuery() → filters list by text or category (case-insensitive) → updates filteredReminders state
- **Mark Complete**: Tap "Done"/"Undo" button → toggleCompleted() updates COLUMN_COMPLETED → cancels/reschedules notification
- **Notification Flow**: scheduleNotification() sets AlarmManager → fires broadcast → ReminderNotificationReceiver.onReceive() → NotificationScheduler.showNotification() displays in tray

## Integration Points
- **Notifications**: BroadcastReceiver + AlarmManager (no WorkManager). Scheduled in RemindersViewModel.addReminder(). Canceled in deleteReminder() and when toggling complete.
- **Date/Time Picking**: Android framework DatePickerDialog / TimePickerDialog (not custom Compose pickers)
- **External Libs**: ThreeTenABP (com.jakewharton.threetenabp) for accurate date handling; androidx.navigation.compose for screen navigation

## Notes
App fully implements core reminder functionality: CRUD operations, priority/category tagging, search/filter, edit mode, completion tracking, and basic AlarmManager notifications. No cloud sync, WorkManager, or advanced scheduling. UI uses hardcoded color scheme; no dark mode toggle.

**Testing considerations:** Validate non-blank form fields, test search case-insensitivity, verify DB migrations don't lose data, confirm alarms schedule/cancel correctly, test edit mode data loading, check completed item hiding works.</content>
<parameter name="filePath">C:\Users\Crain\Desktop\Personal Files\Pet-projects\AndroidStudio\Reminder_app\AGENTS.md
