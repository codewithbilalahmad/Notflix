package org.muhammad.notflix.data.repository

import org.muhammad.notflix.domain.repository.SettingsRepository
import org.muhammad.notflix.domain.util.Constants.KEY_IMAGE_QUALITY
import org.muhammad.notflix.domain.util.Constants.KEY_LANGUAGE
import org.muhammad.notflix.domain.util.Constants.KEY_THEME
import org.muhammad.notflix.util.Context
import org.muhammad.notflix.util.getData
import org.muhammad.notflix.util.putData

class SettingsImpl(private val context: Context) : SettingsRepository {

    override suspend fun getThemePreferences(): Int {
        return context.getData(KEY_THEME)!!.toInt()
    }

    override suspend fun setThemePreferences(key: String, value: Int) {
        context.putData(key = key, value = value.toString())
    }

    override suspend fun getLanguagePreferences(): String {
        return context.getData(KEY_LANGUAGE).toString()
    }

    override suspend fun setLanguagePreferences(key: String, value: String) {
        context.putData(key, value)
    }

    override suspend fun getImageQualityPreferences(): Int {
        return context.getData(KEY_IMAGE_QUALITY)!!.toInt()
    }

    override suspend fun setImageQualityPreferences(key: String, value: Int) {
        context.putData(key = key, value = value.toString())
    }

}