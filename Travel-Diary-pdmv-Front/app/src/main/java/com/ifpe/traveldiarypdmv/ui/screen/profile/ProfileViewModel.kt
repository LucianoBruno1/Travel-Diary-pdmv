package com.ifpe.traveldiarypdmv.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifpe.traveldiarypdmv.core.network.TravelDiaryRemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProfileViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            is ProfileUiEvent.LoadProfile -> loadProfile(event.userId, event.token)
        }
    }

    private fun loadProfile(userId: String, token: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val result = TravelDiaryRemoteDataSource.getProfile(userId, token)

                result.onSuccess { response ->
                    _uiState.value = ProfileUiState(
                        name = response.name,
                        email = response.email,
                        birthDate = response.birthDate ?: "Não informado",
                        profilePicture = response.profilePicture,
                        bio = response.bio ?: "Conte-nos sobre você...",
                        isLoading = false
                    )
                }.onFailure { exception ->
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = exception.message)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }
}