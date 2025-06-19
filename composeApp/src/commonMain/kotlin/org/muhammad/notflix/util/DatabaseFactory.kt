package org.muhammad.notflix.util

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseFactory() {
    fun createDriver(): SqlDriver
}
