package org.tavioribeiro.adb_manager.core_ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.tavioribeiro.adb_manager.core.data.local.local_storage.LocalStorage

object ThemeState {
    private lateinit var localStorage: LocalStorage


    private const val THEME_DARK = "dark"
    private const val THEME_LIGHT = "light"

    var currentColors by mutableStateOf(darkColors)
        private set


    fun initialize(storage: LocalStorage) {
        localStorage = storage
        val savedTheme = localStorage.getTheme()

        currentColors = if (savedTheme == THEME_LIGHT) {
            lightColors
        } else {
            darkColors
        }
    }

    fun toggleTheme() {
        if (currentColors == darkColors) {
            currentColors = lightColors
            localStorage.saveTheme(THEME_LIGHT)
        } else {
            currentColors = darkColors
            localStorage.saveTheme(THEME_DARK)
        }
    }
}