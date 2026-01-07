package org.tavioribeiro.adb_manager.feature_parking.domain.repository.sync



import org.tavioribeiro.adb_manager.core.data.local.session_cache.SessionCache
import org.tavioribeiro.adb_manager.db.AppDatabase
import org.tavioribeiro.adb_manager.feature_parking.data.remote.services.ParkingApiService


class SyncRepositoryImpl(
    private val apiService: ParkingApiService,
    private val db: AppDatabase,
    private val sessionCache: SessionCache
) : SyncRepository {

    override suspend fun syncData(): Result<Unit> {
        return runCatching {
            val session = sessionCache.getSession() ?: throw Exception("Usuário não logado")

            val response = apiService.fetchManualLoad(session.userId, session.establishmentId)

            db.transaction {
                db.appDatabaseQueries.deleteAllPriceTables()

                response.data.prices.forEach { priceTableDto ->
                    val firstItem = priceTableDto.items.firstOrNull()

                    if (firstItem != null) {
                        db.appDatabaseQueries.insertPriceTable(
                            id = priceTableDto.name.hashCode().toLong(),
                            name = priceTableDto.name,
                            toleranceMinutes = (priceTableDto.tolerance / 60).toLong(),
                            hourlyPrice = firstItem.price.toDoubleOrNull() ?: 0.0,
                            additionalPrice = 0.0
                        )
                    }
                }

                db.appDatabaseQueries.deleteAllPaymentMethods()
                response.data.paymentMethods.forEach { paymentDto ->
                    db.appDatabaseQueries.insertPaymentMethod(
                        id = paymentDto.id.toLong(),
                        name = paymentDto.name
                    )
                }
            }
        }
    }
}