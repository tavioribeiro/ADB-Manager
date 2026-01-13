package org.tavioribeiro.adb_manager.core.di


import org.tavioribeiro.adb_manager.core.data.local.database_driver.DatabaseDriverFactory
import org.tavioribeiro.adb_manager.core.data.local.local_storage.LocalStorage
import org.tavioribeiro.adb_manager.core.data.local.local_storage.LocalStorageImpl
import org.tavioribeiro.adb_manager.core.data.remote.KtorClientFactory
import org.tavioribeiro.adb_manager.db.AppDatabase
import org.koin.dsl.module


val coreModule = module {
    single<LocalStorage> { LocalStorageImpl(settings = get()) }

    single { KtorClientFactory.create(localStorage = get()) }

    single<AppDatabase> {
        val driver = get<DatabaseDriverFactory>().createDriver()
        AppDatabase(driver)
    }
}