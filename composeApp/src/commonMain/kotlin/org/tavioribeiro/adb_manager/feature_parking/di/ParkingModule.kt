package org.tavioribeiro.adb_manager.feature_parking.di


import org.tavioribeiro.adb_manager.feature_parking.data.remote.services.ParkingApiService
import org.tavioribeiro.adb_manager.feature_parking.data.remote.services.ParkingApiServiceImpl
import org.tavioribeiro.adb_manager.feature_parking.domain.repository.parking.ParkingRepository
import org.tavioribeiro.adb_manager.feature_parking.domain.repository.parking.ParkingRepositoryImpl
import org.tavioribeiro.adb_manager.feature_parking.domain.repository.sync.SyncRepository
import org.tavioribeiro.adb_manager.feature_parking.domain.repository.sync.SyncRepositoryImpl
import org.tavioribeiro.adb_manager.feature_parking.domain.usecase.CalculateParkingFeeUseCase
import org.tavioribeiro.adb_manager.feature_parking.presentation.dashboard.DashboardViewModel
import org.tavioribeiro.adb_manager.feature_parking.presentation.entry.EntryViewModel
import org.tavioribeiro.adb_manager.feature_parking.presentation.vehicle_list.VehicleListViewModel
import org.koin.dsl.module

val parkingModule = module {
    single<ParkingApiService> { ParkingApiServiceImpl(httpClient = get()) }

    single<SyncRepository> {
        SyncRepositoryImpl(apiService = get(), db = get(), sessionCache = get())
    }

    single<ParkingRepository> {
        ParkingRepositoryImpl(db = get())
    }

    factory { CalculateParkingFeeUseCase() }

    factory {
        DashboardViewModel(
            parkingRepository = get(),
            syncRepository = get(),
            authRepository = get()
        )
    }

    factory {
        EntryViewModel(parkingRepository = get())
    }

    factory {
        VehicleListViewModel(
            parkingRepository = get(),
            calculateParkingFeeUseCase = get()
        )
    }
}