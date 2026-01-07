package org.tavioribeiro.adb_manager.feature_auth.di

import org.tavioribeiro.adb_manager.feature_auth.data.remote.AuthApiService
import org.tavioribeiro.adb_manager.feature_auth.data.remote.AuthApiServiceImpl
import org.tavioribeiro.adb_manager.feature_auth.data.repository.AuthRepositoryImpl
import org.tavioribeiro.adb_manager.feature_auth.domain.repository.AuthRepository
import org.tavioribeiro.adb_manager.feature_auth.presentation.login.LoginScreenModel
import org.koin.dsl.module


val authModule = module {
    single<AuthApiService> { AuthApiServiceImpl(httpClient = get()) }

    single<AuthRepository> {
        AuthRepositoryImpl(apiService = get(), sessionCache = get(), db = get())
    }

    factory { LoginScreenModel(authRepository = get()) }
}