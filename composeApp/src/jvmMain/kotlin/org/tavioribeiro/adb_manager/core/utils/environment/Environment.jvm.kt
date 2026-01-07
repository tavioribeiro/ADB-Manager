package org.tavioribeiro.adb_manager.core.utils.environment

actual object EnvironmentProvider {

    private val rawEnv: String = System.getProperty("app.env") ?: System.getenv("APP_ENV") ?: "dev"

    actual val environment: AppEnvironment = when (rawEnv) {
            "prod" -> AppEnvironment.PROD
            "homol" -> AppEnvironment.HOMOL
            else -> AppEnvironment.DEV
        }
}