package org.tavioribeiro.adb_manager.feature_main.domain.repository

import org.tavioribeiro.adb_manager.feature_main.domain.model.UserSession

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<UserSession>
    suspend fun logout(): Result<Unit>
    fun getCurrentSession(): UserSession?
}