package org.tavioribeiro.adb_manager.di

import org.tavioribeiro.adb_manager.core.di.coreModule
import org.tavioribeiro.adb_manager.feature_main.di.dashboardModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}, platformModule: Module) {
    startKoin {
        appDeclaration()
        modules(
            platformModule,
            coreModule,
            dashboardModule
        )
    }
}