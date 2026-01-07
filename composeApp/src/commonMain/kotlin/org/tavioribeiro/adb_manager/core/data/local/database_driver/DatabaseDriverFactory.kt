package org.tavioribeiro.adb_manager.core.data.local.database_driver

import app.cash.sqldelight.db.SqlDriver

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}