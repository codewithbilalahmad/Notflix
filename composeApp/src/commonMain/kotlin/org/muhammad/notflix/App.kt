package org.muhammad.notflix

import androidx.compose.runtime.Composable
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.muhammad.notflix.di.commonModule
import org.muhammad.notflix.ui.screens.main.MainScreen
import org.muhammad.notflix.ui.viewModel.MovieViewModel
import org.muhammad.notflix.util.Context
import org.muhammad.notflix.util.getAsyncImageLoader

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App(context : Context) {
    setSingletonImageLoaderFactory {con ->
        con.getAsyncImageLoader()
    }
    KoinApplication(application = {
        modules(commonModule(context))
    }){
        val viewModel = koinInject<MovieViewModel>()
        MainScreen(viewModel)
    }
}