package org.muhammad.notflix.util

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.muhammad.notflix.NotflixDatabase
import java.io.File

actual class DatabaseFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        if (!File("NotflixDatabase.db").exists()) {
            NotflixDatabase.Schema.create(driver)
        }
        return driver
    }
}