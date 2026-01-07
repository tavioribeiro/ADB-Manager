package org.tavioribeiro.adb_manager.feature_parking.presentation.vehicle_list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.tavioribeiro.adb_manager.core_ui.components.toast.model.ToastType
import org.tavioribeiro.adb_manager.core_ui.components.toast.model.ToastUiModel
import org.tavioribeiro.adb_manager.feature_parking.domain.model.Vehicle
import org.tavioribeiro.adb_manager.feature_parking.domain.repository.parking.ParkingRepository
import org.tavioribeiro.adb_manager.feature_parking.domain.usecase.CalculateParkingFeeUseCase
import kotlin.time.ExperimentalTime


data class CheckoutState(
    val vehicle: Vehicle? = null,
    val exitTime: LocalDateTime? = null,
    val durationLabel: String = "",
    val totalAmount: Double = 0.0,
    val isVisible: Boolean = false
)

data class VehicleListUiState(
    val checkout: CheckoutState = CheckoutState(),
    val toast: ToastUiModel? = null,
    val isLoading: Boolean = false
)

class VehicleListViewModel(
    private val parkingRepository: ParkingRepository,
    private val calculateParkingFeeUseCase: CalculateParkingFeeUseCase
) : ScreenModel {

    private val _uiState = MutableStateFlow(VehicleListUiState())
    val uiState = _uiState.asStateFlow()

    val vehicles = parkingRepository.getParkedVehicles()
        .stateIn(screenModelScope, SharingStarted.WhileSubscribed(), emptyList())

    @OptIn(ExperimentalTime::class)
    fun onVehicleSelected(vehicle: Vehicle) {
        screenModelScope.launch {
            try {
                val exitTime = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

                val tables = parkingRepository.getPriceTables()
                val table = tables.find { it.id == vehicle.priceTableId }

                if (table == null) {
                    _uiState.update { it.copy(toast = ToastUiModel("Erro", "Tabela de preço não encontrada!", ToastType.ERROR)) }
                    return@launch
                }


                val amount = calculateParkingFeeUseCase(
                    checkIn = vehicle.checkInTime,
                    checkOut = exitTime,
                    priceTable = table
                )

                _uiState.update {
                    it.copy(
                        checkout = CheckoutState(
                            vehicle = vehicle,
                            exitTime = exitTime,
                            totalAmount = amount,
                            isVisible = true
                        )
                    )
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(toast = ToastUiModel("Erro", "Falha ao calcular: ${e.message}", ToastType.ERROR)) }
            }
        }
    }

    fun confirmCheckout() {
        val checkoutState = _uiState.value.checkout
        val vehicle = checkoutState.vehicle ?: return
        val exitTime = checkoutState.exitTime ?: return
        val amount = checkoutState.totalAmount

        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val updatedVehicle = vehicle.copy(
                checkOutTime = exitTime,
                amountPaid = amount
            )

            parkingRepository.checkOut(updatedVehicle)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            checkout = CheckoutState(isVisible = false),
                            toast = ToastUiModel("Sucesso", "Saída registrada! Valor: R$ $amount", ToastType.SUCCESS)
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            toast = ToastUiModel("Erro", error.message ?: "Falha ao dar saída", ToastType.ERROR)
                        )
                    }
                }
        }
    }

    fun dismissCheckout() {
        _uiState.update { it.copy(checkout = CheckoutState(isVisible = false)) }
    }

    fun clearToast() {
        _uiState.update { it.copy(toast = null) }
    }
}