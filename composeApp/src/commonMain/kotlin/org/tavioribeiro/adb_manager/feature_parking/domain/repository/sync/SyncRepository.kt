package org.tavioribeiro.adb_manager.feature_parking.domain.repository.sync

interface SyncRepository {
    suspend fun syncData(): Result<Unit>
}