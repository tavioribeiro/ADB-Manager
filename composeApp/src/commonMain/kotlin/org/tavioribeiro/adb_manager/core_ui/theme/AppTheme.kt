package org.tavioribeiro.adb_manager.core_ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf


val localAppColors = staticCompositionLocalOf { darkColors }

object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = localAppColors.current
}

@Composable
fun MyAppTheme(
    content: @Composable () -> Unit
) {
    val currentColors = ThemeState.currentColors

    CompositionLocalProvider(localAppColors provides currentColors) {
        MaterialTheme(
            content = content,
            typography = FigtreeTypography()
        )
    }
}
