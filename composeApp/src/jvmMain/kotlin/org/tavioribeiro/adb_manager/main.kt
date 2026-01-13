package org.tavioribeiro.adb_manager

import adbmanager.composeapp.generated.resources.Res
import adbmanager.composeapp.generated.resources.logo_png
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.russhwolf.settings.PreferencesSettings
import org.jetbrains.compose.resources.painterResource
import org.tavioribeiro.adb_manager.core.data.local.DesktopDatabaseDriverFactory
import org.tavioribeiro.adb_manager.core.data.local.database_driver.DatabaseDriverFactory
import org.tavioribeiro.adb_manager.di.initKoin
import org.koin.dsl.module
import java.awt.Dimension
import java.util.prefs.Preferences

fun main() = application {
    initKoin(
        platformModule = module {
            single<com.russhwolf.settings.Settings> {
                val preferences = Preferences.userRoot().node("adb_manager_prefs")
                PreferencesSettings(preferences)
            }

            single<DatabaseDriverFactory> {
                DesktopDatabaseDriverFactory()
            }
        }
    )

    val windowInitialState = rememberWindowState(
        placement = WindowPlacement.Maximized
        //width = 600.dp,
        //height = 804.dp
    )


    Window(
        onCloseRequest = ::exitApplication,
        title = "ADB Manager",
        icon = painterResource(Res.drawable.logo_png),
        state = windowInitialState,
    ) {
        window.minimumSize = Dimension(1500, 700)
        App()
    }
}