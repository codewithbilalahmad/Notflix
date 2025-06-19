package org.muhammad.notflix.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.muhammad.notflix.NotflixDatabase
import org.muhammad.notflix.data.local.DataSourceImpl
import org.muhammad.notflix.data.local.DataStore
import org.muhammad.notflix.data.remote.MovieClientApi
import org.muhammad.notflix.data.repository.MovieServiceImpl
import org.muhammad.notflix.data.repository.SettingsImpl
import org.muhammad.notflix.domain.repository.SettingsRepository
import org.muhammad.notflix.ui.viewModel.MovieViewModel
import org.muhammad.notflix.util.AppDataStoreManager
import org.muhammad.notflix.util.Context
import org.muhammad.notflix.util.DatabaseFactory

fun commonModule(context: Context) = module {
    single {
        HttpClient{
           install(ContentNegotiation){
               json(json = Json {
                   prettyPrint = true
                   ignoreUnknownKeys = true
               })
           }
            install(Logging){
                level = LogLevel.ALL
                logger = object : Logger{
                    override fun log(message: String) {
                        println(message)
                    }
                }
            }
        }
    }
    single { NotflixDatabase(DatabaseFactory().createDriver()) }
    single { DataSourceImpl(get()) }
    single { MovieClientApi(get()) }
    single{AppDataStoreManager(context)}
    single { MovieViewModel(get(), get(), get()) }
    single { SettingsImpl(context) }
    single{ MovieServiceImpl(get())  }
}