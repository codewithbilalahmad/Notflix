package org.muhammad.notflix

import android.app.Application

class NotflixApplication() : Application() {
    companion object{
        lateinit var INSTANCE : NotflixApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}