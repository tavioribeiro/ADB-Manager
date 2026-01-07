package org.tavioribeiro.adb_manager.feature_parking.data.remote.mappers

import org.tavioribeiro.adb_manager.feature_parking.data.remote.dto.PaymentMethodDto
import org.tavioribeiro.adb_manager.feature_parking.data.remote.dto.PriceItemDto
import org.tavioribeiro.adb_manager.feature_parking.data.remote.dto.PriceTableDto
import org.tavioribeiro.adb_manager.feature_parking.domain.model.PaymentMethod
import org.tavioribeiro.adb_manager.feature_parking.domain.model.PriceRule
import org.tavioribeiro.adb_manager.feature_parking.domain.model.PriceTable



fun PaymentMethodDto.toDomain(): PaymentMethod {
    return PaymentMethod(
        id = this.id,
        name = this.name
    )
}

fun PriceTableDto.toDomain(id: Int): PriceTable {
    return PriceTable(
        id = id,
        name = this.name,
        toleranceInMinutes = this.tolerance / 60,
        rules = this.items.map { it.toDomain() }
    )
}

fun PriceItemDto.toDomain(): PriceRule {
    return PriceRule(
        price = this.price.toDoubleOrNull() ?: 0.0,
        periodInMinutes = this.period / 60,
        activeAfterMinutes = this.since / 60
    )
}