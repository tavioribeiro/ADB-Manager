package org.tavioribeiro.adb_manager.feature_parking.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ManualLoadResponse(
    val response: String,
    val data: ManualLoadDataDto
)

@Serializable
data class ManualLoadDataDto(
    val paymentMethods: List<PaymentMethodDto>,
    val prices: List<PriceTableDto>
    //outras keys
)

@Serializable
data class PaymentMethodDto(
    @SerialName("establishmentPaymentMethodId") val id: Int,
    @SerialName("paymentMethodName") val name: String
)

@Serializable
data class PriceTableDto(
    @SerialName("typePrice") val name: String,
    val tolerance: Int,
    val items: List<PriceItemDto>
)

@Serializable
data class PriceItemDto(
    val itemId: Int,
    val price: String,
    val period: Int,
    val since: Int
)