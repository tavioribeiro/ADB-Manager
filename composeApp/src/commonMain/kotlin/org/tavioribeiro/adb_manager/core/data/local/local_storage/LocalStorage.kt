package org.tavioribeiro.adb_manager.core.data.local.local_storage


interface LocalStorage {
    fun saveTheme(theme: String)
    fun getTheme(): String?


    fun saveLanguage(theme: String)
    fun getLanguage(): String?


    fun clearSettings()
}