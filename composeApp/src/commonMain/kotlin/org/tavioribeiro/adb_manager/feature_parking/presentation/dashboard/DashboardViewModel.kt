package org.tavioribeiro.adb_manager.feature_parking.presentation.dashboard

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tavioribeiro.adb_manager.core_ui.components.toast.model.ToastType
import org.tavioribeiro.adb_manager.core_ui.components.toast.model.ToastUiModel
import org.tavioribeiro.adb_manager.feature_main.domain.repository.AuthRepository
import org.tavioribeiro.adb_manager.feature_parking.domain.repository.parking.ParkingRepository
import org.tavioribeiro.adb_manager.feature_parking.domain.repository.sync.SyncRepository


data class DashboardUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val establishmentId: Int = 0,
    val toast: ToastUiModel? = null
)

class DashboardViewModel(
    private val parkingRepository: ParkingRepository,
    private val syncRepository: SyncRepository,
    private val authRepository: AuthRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()


    val parkedCount = parkingRepository.getParkedVehicles()
        .map { it.size }
        .stateIn(screenModelScope, SharingStarted.WhileSubscribed(), 0)

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        val session = authRepository.getCurrentSession()
        if (session != null) {
            _uiState.update {
                it.copy(userName = session.userName, establishmentId = session.establishmentId)
            }
        }
    }

    fun syncData() {
        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            syncRepository.syncData()
                .onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            toast = ToastUiModel("Sucesso", "Dados sincronizados com sucesso!", ToastType.SUCCESS)
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            toast = ToastUiModel("Erro no Sync", error.message ?: "Falha ao baixar dados.", ToastType.ERROR)
                        )
                    }
                }
        }
    }

    fun logout() {
        screenModelScope.launch {
            authRepository.logout()
        }
    }

    fun clearToast() {
        _uiState.update { it.copy(toast = null) }
    }
}