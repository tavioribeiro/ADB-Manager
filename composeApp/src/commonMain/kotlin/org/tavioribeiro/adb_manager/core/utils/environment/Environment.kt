package org.tavioribeiro.adb_manager.core.utils.environment

enum class AppEnvironment {
    DEV,
    HOMOL,
    PROD
}

expect object EnvironmentProvider {
    val environment: AppEnvironment
}