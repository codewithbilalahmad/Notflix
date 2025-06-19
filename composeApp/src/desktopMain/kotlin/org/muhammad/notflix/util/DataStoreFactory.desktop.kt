package org.muhammad.notflix.util

import java.util.prefs.Preferences

actual class Context

private val prefernces = Preferences.userRoot().node(APP_DATASTORE)

actual suspend fun Context.putData(
    key: String,
    value: String,
) {
    prefernces.put(key, value)
    prefernces.flush()
}

actual suspend fun Context.getData(key: String): String? {
    return prefernces.get(key, null)
}