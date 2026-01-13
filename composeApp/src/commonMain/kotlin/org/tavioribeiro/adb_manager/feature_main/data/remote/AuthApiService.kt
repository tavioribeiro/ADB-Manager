package org.tavioribeiro.adb_manager.feature_main.data.remote

import org.tavioribeiro.adb_manager.feature_main.data.remote.dto.CloseSessionResponseDto
import org.tavioribeiro.adb_manager.feature_main.data.remote.dto.LoginResponseDto

interface AuthApiService {
    suspend fun login(email: String, password: String): LoginResponseDto
    suspend fun closeSession(userId: Int, establishmentId: Int, sessionId: Int, dateTime: String): CloseSessionResponseDto
}