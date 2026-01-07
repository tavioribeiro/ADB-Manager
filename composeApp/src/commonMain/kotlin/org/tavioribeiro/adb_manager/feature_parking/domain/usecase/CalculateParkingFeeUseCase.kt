package org.tavioribeiro.adb_manager.feature_parking.domain.usecase

import org.tavioribeiro.adb_manager.feature_parking.domain.model.PriceTable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.TimeZone
import kotlin.math.ceil
import kotlin.time.ExperimentalTime


class CalculateParkingFeeUseCase {

    @OptIn(ExperimentalTime::class)
    operator fun invoke(
        checkIn: LocalDateTime,
        checkOut: LocalDateTime,
        priceTable: PriceTable
    ): Double {

        val timeZone = TimeZone.currentSystemDefault()
        val entryInstant = checkIn.toInstant(timeZone)
        val exitInstant = checkOut.toInstant(timeZone)

        val durationInMinutes = (exitInstant.epochSeconds - entryInstant.epochSeconds) / 60.0


        if (durationInMinutes <= priceTable.toleranceInMinutes) {
            return 0.0
        }

        val rule = priceTable.rules.firstOrNull() ?: return 0.0
        val pricePerHour = rule.price

        val hoursCharged = ceil(durationInMinutes / 60.0).toInt()

        return hoursCharged * pricePerHour
    }
}
