package org.muhammad.notflix.util


expect class Context

expect suspend fun Context.putData(key : String, value : String)
expect suspend fun Context.getData(key : String) : String?