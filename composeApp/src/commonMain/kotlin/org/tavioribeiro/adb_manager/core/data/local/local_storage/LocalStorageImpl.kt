package org.tavioribeiro.adb_manager.core.data.local.local_storage

import com.russhwolf.settings.Settings

class LocalStorageImpl(
    private val settings: Settings
) : LocalStorage {

    companion object {
        private const val KEY_THEME = "app_theme"
        private const val KEY_LANGUAGE = "app_language"
    }

    override fun saveTheme(theme: String) {
        settings.putString(KEY_THEME, theme)
    }

    override fun getTheme(): String? {
        return settings.getStringOrNull(KEY_THEME)
    }

    override fun saveLanguage(language: String) {
        settings.putString(KEY_LANGUAGE, language)
    }

    override fun getLanguage(): String? {
        return settings.getStringOrNull(KEY_LANGUAGE)
    }

    override fun clearSettings() {
        settings.clear()
    }
}