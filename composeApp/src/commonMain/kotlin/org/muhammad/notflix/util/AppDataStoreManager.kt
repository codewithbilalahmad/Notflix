package org.muhammad.notflix.util

const val APP_DATASTORE = "Settings"

class AppDataStoreManager(private val context : Context) : AppDataStore{
    override suspend fun setValue(key: String, value: String) {
        context.putData(key, value)
    }

    override suspend fun getValue(key: String): String? {
      return  context.getData(key)
    }
}