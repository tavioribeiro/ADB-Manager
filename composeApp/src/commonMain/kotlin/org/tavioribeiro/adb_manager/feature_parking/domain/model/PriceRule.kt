package org.tavioribeiro.adb_manager.feature_parking.domain.model

data class PriceRule(
    val price: Double,
    val periodInMinutes: Int,
    val activeAfterMinutes: Int
)