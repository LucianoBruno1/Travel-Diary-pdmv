package com.ifpe.traveldiarypdmv.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifpe.traveldiarypdmv.core.network.TravelDiaryRemoteDataSource
import com.ifpe.traveldiarypdmv.data.model.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnLoginClicked -> login(event.email, event.password)
            is LoginUiEvent.OnResetError -> resetError()
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(isLoading = true, errorMessage = null)
            println("Iniciando login com email: $email") // Log de depuração
            val result = TravelDiaryRemoteDataSource.login(LoginRequest(email, password))
            println("Resultado da requisição: $result") // Log de depuração

            val response = result.getOrNull()

            _uiState.value = if (result.isSuccess && response?.token != null) {

                println("Login bem-sucedido")
                uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    token = response.token
                )
            } else {
                val errorMessage = result.exceptionOrNull()?.message
                    ?: result.getOrNull()?.message // Pega a mensagem da API, se existir
                    ?: "Erro desconhecido" // Mensagem padrão
                println("Erro ao fazer login: $errorMessage")
                uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = false,
                    errorMessage = errorMessage
                )
            }
        }
    }

    private fun resetError() {
        _uiState.value = uiState.value.copy(errorMessage = null)
    }
}