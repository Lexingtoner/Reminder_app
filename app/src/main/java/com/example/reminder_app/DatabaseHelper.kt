package com.example.reminder_app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "reminders.db"
        private const val DATABASE_VERSION = 2
        const val TABLE_NAME = "reminders"
        const val COLUMN_ID = "id"
        const val COLUMN_TEXT = "text"
        const val COLUMN_DATE = "date"
        const val COLUMN_TIME = "time"
        const val COLUMN_PRIORITY = "priority"
        const val COLUMN_COMPLETED = "completed"
        const val COLUMN_CATEGORY = "category"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME(
                $COLUMN_ID INTEGER PRIMARY KEY,
                $COLUMN_TEXT TEXT,
                $COLUMN_DATE TEXT,
                $COLUMN_TIME TEXT,
                $COLUMN_PRIORITY TEXT DEFAULT 'normal',
                $COLUMN_COMPLETED INTEGER DEFAULT 0,
                $COLUMN_CATEGORY TEXT DEFAULT 'Other'
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            try {
                db?.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_PRIORITY TEXT DEFAULT 'normal'")
                db?.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_COMPLETED INTEGER DEFAULT 0")
                db?.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_CATEGORY TEXT DEFAULT 'Other'")
            } catch (e: Exception) {
                val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
                db?.execSQL(dropTableQuery)
                onCreate(db)
            }
        }
    }
}