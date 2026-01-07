package org.tavioribeiro.adb_manager.feature_parking.data.remote.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.tavioribeiro.adb_manager.feature_parking.data.remote.dto.ManualLoadResponse

class ParkingApiServiceImpl(
    private val httpClient: HttpClient
) : ParkingApiService {

    override suspend fun fetchManualLoad(userId: Int, establishmentId: Int): ManualLoadResponse {
        return httpClient.get("$userId/establishment/$establishmentId/sync/manual").body()
    }
}