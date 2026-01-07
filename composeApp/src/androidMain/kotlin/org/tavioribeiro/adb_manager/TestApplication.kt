package org.tavioribeiro.adb_manager

import android.app.Application
import com.russhwolf.settings.SharedPreferencesSettings
import org.tavioribeiro.adb_manager.core.data.local.AndroidDatabaseDriverFactory
import org.tavioribeiro.adb_manager.core.data.local.database_driver.DatabaseDriverFactory
import org.tavioribeiro.adb_manager.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.module

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(
            appDeclaration = {
                androidLogger()
                androidContext(this@TestApplication)
            },
            platformModule = module {
                single<com.russhwolf.settings.Settings> {
                    val sharedPrefs = get<android.content.Context>()
                        .getSharedPreferences("add_manager_prefs", android.content.Context.MODE_PRIVATE)
                    SharedPreferencesSettings(sharedPrefs)
                }

                single<DatabaseDriverFactory> {
                    AndroidDatabaseDriverFactory(context = get())
                }
            }
        )
    }
}