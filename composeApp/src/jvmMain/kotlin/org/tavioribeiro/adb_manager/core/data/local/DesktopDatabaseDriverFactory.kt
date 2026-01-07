package org.tavioribeiro.adb_manager.core.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.tavioribeiro.adb_manager.core.data.local.database_driver.DatabaseDriverFactory
import org.tavioribeiro.adb_manager.db.AppDatabase
import java.io.File

class DesktopDatabaseDriverFactory : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        val databasePath = File(System.getProperty("java.io.tmpdir"), "adb_manager.db")
        val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")


        if (!databasePath.exists()) {
            AppDatabase.Schema.create(driver)
        }
        return driver
    }
}