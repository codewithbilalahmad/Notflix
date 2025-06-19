package org.muhammad.notflix.util

import kotlinx.browser.window

actual class Context

actual suspend fun Context.putData(
    key: String,
    value: String,
) {
    window.localStorage.setItem(key, value)
}

actual suspend fun Context.getData(key: String): String? {
    return window.localStorage.getItem(key)
}