package org.tavioribeiro.adb_manager.feature_main.di

import org.tavioribeiro.adb_manager.feature_main.data.remote.AuthApiService
import org.tavioribeiro.adb_manager.feature_main.data.remote.AuthApiServiceImpl
import org.tavioribeiro.adb_manager.feature_main.data.repository.AuthRepositoryImpl
import org.tavioribeiro.adb_manager.feature_main.domain.repository.AuthRepository
import org.tavioribeiro.adb_manager.feature_main.presentation.main.LoginScreenModel
import org.koin.dsl.module


val authModule = module {
    single<AuthApiService> { AuthApiServiceImpl(httpClient = get()) }

    single<AuthRepository> {
        AuthRepositoryImpl(apiService = get(), sessionCache = get(), db = get())
    }

    factory { LoginScreenModel(authRepository = get()) }
}