package org.tavioribeiro.adb_manager.feature_parking.domain.model

import kotlinx.datetime.LocalDateTime

data class Vehicle(
    val plate: String,
    val model: String,
    val color: String,
    val checkInTime: LocalDateTime,
    val priceTableId: Int,
    val checkOutTime: LocalDateTime? = null,
    val amountPaid: Double? = null
)