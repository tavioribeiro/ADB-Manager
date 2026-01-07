package org.tavioribeiro.adb_manager.feature_parking.domain.usecase

import kotlinx.datetime.LocalDateTime
import org.tavioribeiro.adb_manager.feature_parking.domain.model.PriceRule
import org.tavioribeiro.adb_manager.feature_parking.domain.model.PriceTable
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateParkingFeeUseCaseTest {

    private val useCase = CalculateParkingFeeUseCase()

    private val defaultRule = PriceRule(price = 10.0, periodInMinutes = 60, activeAfterMinutes = 0)
    private val priceTable = PriceTable(
        id = 1,
        name = "Padrão",
        toleranceInMinutes = 15,
        rules = listOf(defaultRule)
    )

    @Test
    fun `deve retornar 0 quando a duracao estiver dentro da tolerancia`() {
        val checkIn = LocalDateTime(2025, 12, 18, 10, 0, 0)
        val checkOut = LocalDateTime(2025, 12, 18, 10, 10, 0)

        val result = useCase(checkIn, checkOut, priceTable)

        assertEquals(0.0, result, "Deve ser grátis dentro da tolerância")
    }

    @Test
    fun `deve cobrar 1 hora quando a duracao for exatamente 1 hora`() {
        val checkIn = LocalDateTime(2025, 12, 18, 10, 0, 0)
        val checkOut = LocalDateTime(2025, 12, 18, 11, 0, 0)

        val result = useCase(checkIn, checkOut, priceTable)

        assertEquals(10.0, result, "Deve cobrar o valor referente a 1 hora")
    }

    @Test
    fun `deve cobrar 2 horas quando a duracao passar de 1 hora em 1 minuto`() {
        val checkIn = LocalDateTime(2025, 12, 18, 10, 0, 0)
        val checkOut = LocalDateTime(2025, 12, 18, 11, 1, 0)

        val result = useCase(checkIn, checkOut, priceTable)

        assertEquals(20.0, result, "Deve cobrar 2 horas pois iniciou um novo período")
    }
}