package org.tavioribeiro.adb_manager.feature_parking.presentation.entry

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.tavioribeiro.adb_manager.core_ui.components.select.model.SelectOptionModel
import org.tavioribeiro.adb_manager.core_ui.components.toast.model.ToastType
import org.tavioribeiro.adb_manager.core_ui.components.toast.model.ToastUiModel
import org.tavioribeiro.adb_manager.feature_parking.domain.model.Vehicle
import org.tavioribeiro.adb_manager.feature_parking.domain.repository.parking.ParkingRepository
import kotlin.time.ExperimentalTime

data class EntryUiState(
    val isLoading: Boolean = false,
    val priceTables: List<SelectOptionModel> = emptyList(),
    val toast: ToastUiModel? = null,
    val isSaved: Boolean = false // Gatilho para fechar a tela
)

class EntryViewModel(
    private val parkingRepository: ParkingRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow(EntryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadPriceTables()
    }

    private fun loadPriceTables() {
        screenModelScope.launch {
            try {
                val tables = parkingRepository.getPriceTables()
                val options = tables.map { table ->
                    SelectOptionModel(
                        label = table.name,
                        value = table.id.toString()
                    )
                }
                _uiState.update { it.copy(priceTables = options) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(toast = ToastUiModel("Erro", "Falha ao carregar tabelas: ${e.message}", ToastType.ERROR))
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun saveVehicle(plate: String, model: String, color: String, priceTableId: String) {
        if (plate.isBlank() || model.isBlank() || color.isBlank() || priceTableId.isBlank()) {
            _uiState.update {
                it.copy(toast = ToastUiModel("Atenção", "Preencha todos os campos", ToastType.WARNING))
            }
            return
        }

        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Gera a data atual
            val currentMoment = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

            val vehicle = Vehicle(
                plate = plate.uppercase(),
                model = model,
                color = color,
                checkInTime = currentMoment,
                priceTableId = priceTableId.toInt()
            )

            parkingRepository.checkIn(vehicle)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSaved = true,
                            toast = ToastUiModel(
                                "Sucesso",
                                "Veículo registrado!",
                                ToastType.SUCCESS
                            )
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            toast = ToastUiModel("Erro", error.message ?: "Falha ao salvar", ToastType.ERROR)
                        )
                    }
                }
        }
    }

    fun clearToast() {
        _uiState.update { it.copy(toast = null) }
    }
}