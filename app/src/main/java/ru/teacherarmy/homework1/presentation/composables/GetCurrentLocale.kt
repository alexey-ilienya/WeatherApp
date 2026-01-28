package ru.teacherarmy.homework1.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import java.util.Locale

@Composable
@ReadOnlyComposable
fun getCurrentLocale(): Locale {
    val configuration = LocalConfiguration.current
    return ConfigurationCompat.getLocales(configuration).get(0) ?: Locale.getDefault()
}
