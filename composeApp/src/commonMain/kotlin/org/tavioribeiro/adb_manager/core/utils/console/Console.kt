package org.tavioribeiro.adb_manager.core.utils.console

expect class ConsoleDataSource() {
    suspend fun execute(command: String, path: String): Result<String>
}