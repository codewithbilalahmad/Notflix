package org.muhammad.notflix.util

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

actual typealias Context = Application

val Context.dataStore by preferencesDataStore(APP_DATASTORE)

actual suspend fun Context.putData(
    key: String,
    value: String,
) {
    dataStore.edit {prefs ->
        prefs[stringPreferencesKey(key)] = value
    }
}

actual suspend fun Context.getData(key: String): String? {
    return dataStore.data.first()[stringPreferencesKey(key)]?:""
}