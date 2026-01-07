package org.tavioribeiro.adb_manager.core.utils.log

import android.util.Log
import org.tavioribeiro.adb_manager.core.utils.environment.AppEnvironment
import org.tavioribeiro.adb_manager.core.utils.environment.EnvironmentProvider

actual fun log(message: String) {
    when (EnvironmentProvider.environment) {
        AppEnvironment.DEV -> Log.d("AppLog", message)
        AppEnvironment.HOMOL -> Log.d("AppLog", message)
        AppEnvironment.PROD -> return
    }
}