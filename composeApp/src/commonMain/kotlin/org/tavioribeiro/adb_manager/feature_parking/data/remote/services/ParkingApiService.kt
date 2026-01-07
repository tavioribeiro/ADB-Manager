package org.tavioribeiro.adb_manager.feature_parking.data.remote.services

import org.tavioribeiro.adb_manager.feature_parking.data.remote.dto.ManualLoadResponse


interface ParkingApiService {
    suspend fun fetchManualLoad(userId: Int, establishmentId: Int): ManualLoadResponse
}