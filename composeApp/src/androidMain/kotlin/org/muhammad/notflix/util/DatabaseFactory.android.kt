package org.muhammad.notflix.util

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.muhammad.notflix.NotflixApplication
import org.muhammad.notflix.NotflixDatabase

actual class DatabaseFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        val context = NotflixApplication.INSTANCE.applicationContext
        return AndroidSqliteDriver(NotflixDatabase.Schema, context = context, "NotflixDatabase.db")
    }
}