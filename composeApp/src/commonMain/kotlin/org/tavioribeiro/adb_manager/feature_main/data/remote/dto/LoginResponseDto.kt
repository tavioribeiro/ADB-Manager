package org.tavioribeiro.adb_manager.feature_main.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val response: String,
    val data: LoginDataDto
)

@Serializable
data class LoginDataDto(
    val user: UserDto,
    val session: SessionDto
)

@Serializable
data class UserDto(
    @SerialName("userId") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("accessToken") val accessToken: String
)

@Serializable
data class SessionDto(
    @SerialName("sessionId") val id: Int,
    @SerialName("establishmentId") val establishmentId: Int
)