package org.tavioribeiro.adb_manager.feature_main.presentation.main

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.tavioribeiro.adb_manager.core_ui.components.toast.model.ToastType
import org.tavioribeiro.adb_manager.core_ui.components.toast.model.ToastUiModel

import org.tavioribeiro.adb_manager.feature_main.domain.repository.AuthRepository


data class LoginUiState(
    val isLoading: Boolean = false,
    val toast: ToastUiModel? = null,
    val isLoggedIn: Boolean = false
)

class LoginScreenModel(
    private val authRepository: AuthRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _uiState.update {
                it.copy(toast = ToastUiModel("Erro", "Preencha todos os campos", ToastType.WARNING))
            }
            return
        }

        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true, toast = null) }

            authRepository.login(email, pass)
                .onSuccess {
                    _uiState.update { state ->
                        state.copy(isLoading = false, isLoggedIn = true)
                    }
                }
                .onFailure { error ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            toast = ToastUiModel("Falha no Login", error.message ?: "Erro desconhecido", ToastType.ERROR)
                        )
                    }
                }
        }
    }

    fun clearToast() {
        _uiState.update { it.copy(toast = null) }
    }
}
