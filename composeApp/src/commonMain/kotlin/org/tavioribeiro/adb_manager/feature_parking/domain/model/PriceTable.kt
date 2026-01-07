package org.tavioribeiro.adb_manager.feature_parking.domain.model

data class PriceTable(
    val id: Int,
    val name: String,
    val toleranceInMinutes: Int,
    val rules: List<PriceRule>
)

