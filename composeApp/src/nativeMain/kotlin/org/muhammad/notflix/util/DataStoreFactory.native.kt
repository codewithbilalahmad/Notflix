package org.muhammad.notflix.util

actual class Context

actual suspend fun Context.putData(
    key: String,
    value: String,
) {
}

actual suspend fun Context.getData(key: String): String? {
    TODO("Not yet implemented")
}