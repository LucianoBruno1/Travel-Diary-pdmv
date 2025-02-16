package com.ifpe.traveldiarypdmv.ui.screen.camera

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifpe.traveldiarypdmv.core.network.TravelDiaryRemoteDataSource
import com.ifpe.traveldiarypdmv.core.util.LocationProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.net.Uri

class UploadPhotoViewModel : ViewModel() {

    // Atributos que antes eram parâmetros do construtor
    lateinit var travelDiaryRemoteDataSource: TravelDiaryRemoteDataSource
    lateinit var locationProvider: LocationProvider

    private val _uiState = MutableStateFlow(UploadPhotoUiState())
    val uiState: StateFlow<UploadPhotoUiState> = _uiState

    fun onEvent(event: UploadPhotoUiEvent) {
        when (event) {
            is UploadPhotoUiEvent.UploadPhoto -> uploadPhoto(event.uri, event.userId, event.token, event.context)
        }
    }

    private fun uploadPhoto(uri: Uri, userId: String, token: String, context: Context) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // Obtém localização do usuário
                val locationResult = locationProvider.getCurrentLocation()
                if (locationResult == null) {
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Permissão de localização necessária.")
                    return@launch
                }

                val (latitude, longitude) = locationResult

                // Faz o upload da foto
                val result = travelDiaryRemoteDataSource.uploadPhoto(uri, userId, latitude, longitude, token, context)

                result.onSuccess {
                    _uiState.value = UploadPhotoUiState(successMessage = "Foto enviada com sucesso!", isLoading = false)
                }.onFailure { exception ->
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = exception.message)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }
}
