package org.tavioribeiro.adb_manager.feature_parking.domain.repository.parking

import androidx.annotation.RequiresApi
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.tavioribeiro.adb_manager.db.AppDatabase
import org.tavioribeiro.adb_manager.feature_parking.domain.model.PriceRule
import org.tavioribeiro.adb_manager.feature_parking.domain.model.PriceTable
import org.tavioribeiro.adb_manager.feature_parking.domain.model.Vehicle
import kotlinx.datetime.LocalDateTime

class ParkingRepositoryImpl(
    private val db: AppDatabase
) : ParkingRepository {

    private val queries = db.appDatabaseQueries

    override fun getParkedVehicles(): Flow<List<Vehicle>> {
        return queries.getVehiclesParked()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities ->
                entities.map { entity ->
                    Vehicle(
                        plate = entity.plate,
                        model = entity.model,
                        color = entity.color,
                        checkInTime = LocalDateTime.parse(entity.checkInTime),
                        priceTableId = entity.priceTableId.toInt()
                    )
                }
            }
    }

    override suspend fun getPriceTables(): List<PriceTable> {
        return queries.getAllPriceTables().executeAsList().map { entity ->
            PriceTable(
                id = entity.id.toInt(),
                name = entity.name,
                toleranceInMinutes = entity.toleranceMinutes.toInt(),
                rules = listOf(
                    PriceRule(entity.hourlyPrice, 60, 0)
                )
            )
        }
    }

    override suspend fun checkIn(vehicle: Vehicle): Result<Unit> {
        return runCatching {
            queries.insertVehicle(
                plate = vehicle.plate,
                model = vehicle.model,
                color = vehicle.color,
                checkInTime = vehicle.checkInTime.toString(),
                priceTableId = vehicle.priceTableId.toLong()
            )
        }
    }

    override suspend fun checkOut(vehicle: Vehicle): Result<Unit> {
        return runCatching {

            queries.checkoutVehicle(
                checkOutTime = vehicle.checkOutTime?.toString(),
                totalAmount = vehicle.amountPaid,
                paymentMethodId = 1,
                plate = vehicle.plate
            )
        }
    }


    override suspend fun getVehicleByPlate(plate: String): Vehicle? {
        val entity = queries.getVehicleByPlate(plate).executeAsOneOrNull() ?: return null
        return Vehicle(
            plate = entity.plate,
            model = entity.model,
            color = entity.color,
            checkInTime = LocalDateTime.parse(entity.checkInTime),
            priceTableId = entity.priceTableId.toInt()
        )
    }
}
