package com.ifpe.traveldiarypdmv.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifpe.traveldiarypdmv.core.network.TravelDiaryRemoteDataSource
import com.ifpe.traveldiarypdmv.data.model.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.OnRegisterClicked -> register(event.name, event.email, event.password)
            is RegisterUiEvent.OnResetError -> resetError()
        }
    }

    private fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = TravelDiaryRemoteDataSource.register(RegisterRequest(name, email, password))

            _uiState.value = if (result.isSuccess) {
                RegisterUiState(isLoading = false, isRegistered = true)
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Erro desconhecido"
                println("Mensagem de erro recebida: $errorMessage") // Log para depurar
                RegisterUiState(isLoading = false, errorMessage = errorMessage)
            }
        }
    }

    private fun resetError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetAfterSuccess() {
        _uiState.value = RegisterUiState() // Limpa o estado após o sucesso
    }
}