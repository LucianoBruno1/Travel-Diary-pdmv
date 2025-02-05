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

            val result = TravelDiaryRemoteDataSource.login(LoginRequest(email, password))

            _uiState.value = if (result.isSuccess) {
                val response = result.getOrNull()
                if (response?.token != null) {
                    println("Login bem-sucedido")
                    uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        token = response.token,
                        userId = response.user?.id
                    )
                } else {
                    println("Erro inesperado: token nulo")
                    uiState.value.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        errorMessage = "Erro inesperado ao fazer login"
                    )
                }
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Erro desconhecido"
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