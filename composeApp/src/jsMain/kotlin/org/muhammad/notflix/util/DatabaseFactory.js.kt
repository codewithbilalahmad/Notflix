package org.muhammad.notflix.util

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.muhammad.notflix.NotflixDatabase
import org.w3c.dom.Worker

actual class DatabaseFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        val scriptUrl =
            js("import.meta.url.replace('kotlin', 'node_modules/@cashapp/sqldelight-sqljs-worker/sqljs.worker.js')")
        val driver = WebWorkerDriver(scriptUrl as Worker).also { NotflixDatabase.Schema.create(it) }
        return driver
    }
}