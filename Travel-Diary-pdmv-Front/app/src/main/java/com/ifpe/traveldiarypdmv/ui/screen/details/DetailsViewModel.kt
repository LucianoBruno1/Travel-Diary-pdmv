package com.ifpe.traveldiarypdmv.ui.screen.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifpe.traveldiarypdmv.core.network.TravelDiaryRemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState

    fun onEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.LoadDiary -> loadDiary(event.diaryId, event.token)
        }
    }

    private fun loadDiary(diaryId: String, token: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val result = TravelDiaryRemoteDataSource.getDiaryDetails(diaryId, token)

                result.onSuccess { response ->
                    val images = response.photos.map { fixImageUrl(it.file_path) }
                    _uiState.value = _uiState.value.copy(
                        name = response.name,
                        description = response.description ?: "",
                        city = response.city,
                        state = response.state,
                        images = images,
                        isLoading = false,
                    )
                    Log.d("DetailsViewModel", "Imagens carregadas: $images")
                }.onFailure { exception ->
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = exception.message)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }

    private fun fixImageUrl(filePath: String): String {
        return filePath.replace("http://localhost", "http://10.0.2.2")
    }
}
