package org.tavioribeiro.adb_manager.feature_parking.domain.repository.parking

import kotlinx.coroutines.flow.Flow
import org.tavioribeiro.adb_manager.feature_parking.domain.model.PaymentMethod
import org.tavioribeiro.adb_manager.feature_parking.domain.model.PriceTable
import org.tavioribeiro.adb_manager.feature_parking.domain.model.Vehicle

interface ParkingRepository {
    fun getParkedVehicles(): Flow<List<Vehicle>>

    suspend fun getPriceTables(): List<PriceTable>

    suspend fun checkIn(vehicle: Vehicle): Result<Unit>

    suspend fun checkOut(vehicle: Vehicle): Result<Unit>

    suspend fun getVehicleByPlate(plate: String): Vehicle?
}