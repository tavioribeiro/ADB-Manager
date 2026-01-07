package org.tavioribeiro.adb_manager.core_ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ThemeState {
    var currentColors by mutableStateOf(darkColors)
        private set

    fun toggleTheme() {
        currentColors = if (currentColors == darkColors) lightColors else darkColors
    }
}