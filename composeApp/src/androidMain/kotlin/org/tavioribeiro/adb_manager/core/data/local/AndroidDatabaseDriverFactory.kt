package org.tavioribeiro.adb_manager.core.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.tavioribeiro.adb_manager.core.data.local.database_driver.DatabaseDriverFactory
import org.tavioribeiro.adb_manager.db.AppDatabase

class AndroidDatabaseDriverFactory(private val context: Context) : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "adb_manager.db")
    }
}