package org.tavioribeiro.adb_manager.core_ui.di


import org.koin.dsl.module
import org.tavioribeiro.adb_manager.core.data.local.local_storage.LocalStorage
import org.tavioribeiro.adb_manager.core.data.local.local_storage.LocalStorageImpl

val coreUiModule = module {
    single<LocalStorage> { LocalStorageImpl(settings = get()) }
}