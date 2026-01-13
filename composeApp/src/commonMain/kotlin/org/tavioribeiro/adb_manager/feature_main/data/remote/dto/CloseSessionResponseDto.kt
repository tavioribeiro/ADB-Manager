package org.tavioribeiro.adb_manager.feature_main.data.remote.dto


import kotlinx.serialization.Serializable

@Serializable
data class CloseSessionResponseDto(
    val response: String,
    val data: CloseSessionDataDto? = null
)

@Serializable
data class CloseSessionDataDto(
    val session: Int
)