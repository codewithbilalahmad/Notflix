package org.muhammad.notflix.domain.repository

interface SettingsRepository {
    suspend fun getThemePreferences() : Int
    suspend fun setThemePreferences(key : String, value : Int)
    suspend fun getLanguagePreferences() : String
    suspend fun setLanguagePreferences(key : String, value : String)
    suspend fun getImageQualityPreferences() : Int
    suspend fun setImageQualityPreferences(key : String, value : Int)
}