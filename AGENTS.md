# AGENTS.md for Reminder_app

## Architecture Overview
This is an Android reminder app built with Kotlin and Jetpack Compose. It uses SQLite for local data storage and follows MVVM pattern with ViewModels. Key components:
- **DatabaseHelper**: Manages SQLite database with `reminders` table (id, text, date, time)
- **Form**: Compose screen for adding reminders with date/time pickers
- **List**: Compose screen displaying reminders in LazyColumn
- **MainActivity**: Entry point, currently shows basic greeting (needs integration with Form/List)
- **ReminderApplication**: Sets up notification channel and initializes ThreeTenABP

Data flows from Form -> ViewModel -> DatabaseHelper for storage, and List -> ViewModel -> DatabaseHelper for retrieval.

## Key Files & Patterns
- `app/build.gradle.kts`: Uses version catalog (`gradle/libs.versions.toml`) for dependencies. Compose enabled via `buildFeatures { compose = true }`
- `DatabaseHelper.kt`: SQLiteOpenHelper subclass. Table creation: `CREATE TABLE reminders(id INTEGER PRIMARY KEY, text TEXT, date TEXT, time TEXT)`
- `Form.kt`: Uses `RemindersViewModel` for state. Date/time inputs via Android dialogs, formatted as "DD.MM.YYYY" and "HH:MM"
- `List.kt`: Loads reminders on launch with `LaunchedEffect`. Displays via `ReminderItem` (not yet implemented)
- `ReminderApplication.kt`: Initializes `AndroidThreeTen` and creates notification channel "reminders" with default sound

## Conventions
- Package: `com.example.reminder_app`
- UI: Jetpack Compose with Material3. Custom colors referenced as `R.color.*` (e.g., `colorResource(id = R.color.dark_navy)`)
- Strings: Localized via `stringResource(id = R.string.*)` (e.g., `R.string.form_title`)
- Date/Time: Uses ThreeTenABP (backport of java.time). Formatting: date "DD.MM.YYYY", time "HH:MM" with leading zeros via `Utils.addZero()` (Utils class missing)
- ViewModel: Shared `RemindersViewModel` instance across Form/List composables (ViewModel class missing)

## Workflows
- **Build**: Run `./gradlew.bat build` (Windows) or `./gradlew build` (Linux/Mac) from project root
- **Run/Debug**: Use Android Studio or `./gradlew.bat installDebug` to install on device/emulator
- **Tests**: Unit tests in `test/`, instrumentation in `androidTest/`. Currently minimal (only JUnit/Espresso setup)
- **Dependencies**: Managed via version catalog. Add to `libs.versions.toml` and reference as `libs.libraryName`

## Integration Points
- Notifications: Channel "reminders" for scheduling (scheduling logic not implemented)
- External libs: ThreeTenABP for date/time handling

## Notes
App is partially implemented. Missing: `RemindersViewModel`, `ReminderItem` composable, `Utils` class, some string/color resources. MainActivity needs to integrate Form/List screens.</content>
<parameter name="filePath">C:\Users\Crain\Desktop\Personal Files\Pet-projects\AndroidStudio\Reminder_app\AGENTS.md
