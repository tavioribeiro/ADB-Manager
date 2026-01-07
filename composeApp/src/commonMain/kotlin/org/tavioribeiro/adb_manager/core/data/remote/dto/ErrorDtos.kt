package org.tavioribeiro.adb_manager.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse400(
    val response: String? = null,
    val data: String? = null
)

@Serializable
data class ErrorResponse422(
    val message: String? = null,
    val errors: Map<String, List<String>>? = null
)