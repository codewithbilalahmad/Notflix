package org.muhammad.notflix.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import notflix.composeapp.generated.resources.Res
import notflix.composeapp.generated.resources.change_image_quality
import notflix.composeapp.generated.resources.change_language
import notflix.composeapp.generated.resources.change_theme
import notflix.composeapp.generated.resources.def
import notflix.composeapp.generated.resources.image_qualities
import notflix.composeapp.generated.resources.languages
import notflix.composeapp.generated.resources.themes
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.muhammad.notflix.domain.util.Constants.KEY_IMAGE_QUALITY
import org.muhammad.notflix.domain.util.Constants.KEY_LANGUAGE
import org.muhammad.notflix.domain.util.Constants.KEY_THEME
import org.muhammad.notflix.ui.components.appbars.AppBar
import org.muhammad.notflix.ui.components.preferences.DialogPreferences
import org.muhammad.notflix.ui.components.preferences.TextPreferences
import org.muhammad.notflix.ui.viewModel.MovieViewModel

@Composable
fun SettingsScreen(viewModel: MovieViewModel = koinInject()) {
    val themeLabels = stringArrayResource(Res.array.themes)
    val imageQualityLabels = stringArrayResource(Res.array.image_qualities)
    val showThemeDialog = remember { mutableStateOf(false) }
    val showImageQualityDialog = remember { mutableStateOf(false) }
    val showLanguageDialog = remember { mutableStateOf(false) }
    val themeLabel = themeLabels[viewModel.themePrefernces.value]
    val imageQualityLabel = imageQualityLabels[viewModel.qualityPrefernces.value]
    val languageLabel by viewModel.languagePrefernces.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        AppBar("Settings")
    }) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            TextPreferences(
                icon = Icons.Rounded.Language,
                title = stringResource(Res.string.change_language),
                subTitle = languageLabel, onClick = {
                    showLanguageDialog.value = !showLanguageDialog.value
                }
            )
            TextPreferences(
                icon = Icons.Rounded.Lightbulb,
                title = stringResource(Res.string.change_theme),
                subTitle = themeLabel, onClick = {
                    showThemeDialog.value = !showThemeDialog.value
                }
            )
            if (showThemeDialog.value) {
                ChangeTheme(
                    viewModel = viewModel,
                    showDialog = showThemeDialog,
                    currentValue = themeLabel
                )
            }
            if (showLanguageDialog.value) {
                ChangeLanguage(
                    viewModel = viewModel,
                    showDialog = showLanguageDialog,
                    currentValue = null
                )
            }
            TextPreferences(
                icon = Icons.Rounded.Image,
                title = stringResource(Res.string.change_image_quality),
                subTitle = imageQualityLabel, onClick = {
                    showImageQualityDialog.value = !showImageQualityDialog.value
                }
            )
            if (showImageQualityDialog.value) {
                ChannelImageQuality(
                    viewModel = viewModel,
                    showDialog = showImageQualityDialog,
                    currentValue = imageQualityLabel
                )
            }
        }
    }
}

@Composable
fun ChangeLanguage(
    viewModel: MovieViewModel,
    showDialog: MutableState<Boolean>,
    currentValue: String?,
) {
    DialogPreferences(
        showDialog = showDialog.value,
        title = stringResource(Res.string.change_language),
        currentValue = currentValue ?: stringResource(Res.string.def),
        label = stringArrayResource(Res.array.languages),
        onNegativeClick = {
            showDialog.value = false
        },
        onOptionSelected = { language ->
            viewModel.setLanguagePreferences(key = KEY_LANGUAGE, value = language.toString())
        }
    )
}

@Composable
fun ChangeTheme(
    viewModel: MovieViewModel,
    showDialog: MutableState<Boolean>,
    currentValue: String?,
) {
    DialogPreferences(
        showDialog = showDialog.value,
        title = stringResource(Res.string.change_theme),
        currentValue = currentValue ?: stringResource(Res.string.def),
        label = stringArrayResource(Res.array.themes),
        onNegativeClick = {
            showDialog.value = false
        }, onOptionSelected = { theme ->
            viewModel.setThemePreferences(key = KEY_THEME, value = theme.toString())
        }
    )
}

@Composable
private fun ChannelImageQuality(
    viewModel: MovieViewModel,
    showDialog: MutableState<Boolean>,
    currentValue: String?,
) {
    DialogPreferences(
        showDialog = showDialog.value,
        title = stringResource(Res.string.change_image_quality),
        currentValue = currentValue ?: stringResource(Res.string.def),
        label = stringArrayResource(Res.array.image_qualities),
        onNegativeClick = { showDialog.value = false }, onOptionSelected = { imageQuality ->
            viewModel.setImageQualityPreferences(
                key = KEY_IMAGE_QUALITY,
                value = imageQuality.toString()
            )
        }
    )
}