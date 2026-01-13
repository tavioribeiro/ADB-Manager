package org.tavioribeiro.adb_manager.feature_main.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSession(
    val userId: Int,
    val userName: String,
    val establishmentId: Int,
    val sessionId: Int,
    val accessToken: String
)