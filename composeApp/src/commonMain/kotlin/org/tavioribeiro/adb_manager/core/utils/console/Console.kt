package org.tavioribeiro.adb_manager.core.utils.console

expect class ConsoleProvider() {
    suspend fun execute(command: String, path: String): Result<String>
}