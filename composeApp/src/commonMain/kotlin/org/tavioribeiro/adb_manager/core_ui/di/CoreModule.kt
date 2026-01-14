package org.tavioribeiro.adb_manager.core_ui.di


import org.koin.dsl.module
import org.tavioribeiro.adb_manager.core.data.local.local_storage.LocalStorage
import org.tavioribeiro.adb_manager.core.data.local.local_storage.LocalStorageImpl
import org.tavioribeiro.adb_manager.core.utils.console.ConsoleProvider
import org.tavioribeiro.adb_manager.core_ui.components.toast.ToastViewModel



val coreUiModule = module {
    single<ToastViewModel> { ToastViewModel() }
    single<ConsoleProvider> { ConsoleProvider() }


    single<LocalStorage> { LocalStorageImpl(settings = get()) }
}