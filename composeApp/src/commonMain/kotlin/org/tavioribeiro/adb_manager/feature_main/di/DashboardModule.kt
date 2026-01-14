package org.tavioribeiro.adb_manager.feature_main.di

import org.koin.dsl.module
import org.tavioribeiro.adb_manager.feature_main.presentation.dashboard.DashboardScreenViewModel


val dashboardModule = module {

    factory { DashboardScreenViewModel(
        toastViewModel = get(),
        console = get()
    ) }
}