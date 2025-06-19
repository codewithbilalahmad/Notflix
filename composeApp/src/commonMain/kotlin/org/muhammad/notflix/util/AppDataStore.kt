package org.muhammad.notflix.util

interface AppDataStore {
    suspend fun setValue(key : String,value : String)
    suspend fun getValue(key : String) : String?
}