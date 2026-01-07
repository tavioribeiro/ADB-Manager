package org.tavioribeiro.adb_manager.core.utils.environment

import org.tavioribeiro.adb_manager.BuildConfig

actual object EnvironmentProvider {
    actual val environment: AppEnvironment =
        when (BuildConfig.APP_ENV) {
            "prod" -> AppEnvironment.PROD
            "homol" -> AppEnvironment.HOMOL
            else -> AppEnvironment.DEV
        }
}