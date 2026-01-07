package org.tavioribeiro.adb_manager.core.di


import org.tavioribeiro.adb_manager.core.data.local.database_driver.DatabaseDriverFactory
import org.tavioribeiro.adb_manager.core.data.local.session_cache.SessionCache
import org.tavioribeiro.adb_manager.core.data.local.session_cache.SessionCacheImpl
import org.tavioribeiro.adb_manager.core.data.remote.KtorClientFactory
import org.tavioribeiro.adb_manager.db.AppDatabase
import org.koin.dsl.module


val coreModule = module {
    single<SessionCache> { SessionCacheImpl(settings = get()) }

    single { KtorClientFactory.create(sessionCache = get()) }

    single<AppDatabase> {
        val driver = get<DatabaseDriverFactory>().createDriver()
        AppDatabase(driver)
    }
}