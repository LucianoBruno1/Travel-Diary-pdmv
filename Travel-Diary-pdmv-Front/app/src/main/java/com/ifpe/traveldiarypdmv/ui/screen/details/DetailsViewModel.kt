package com.ifpe.traveldiarypdmv.ui.screen.details

import android.R.id
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifpe.traveldiarypdmv.core.network.TravelDiaryRemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState

    fun onEvent(event: DetailsUiEvent) {
        when (event) {
            is DetailsUiEvent.LoadDiary -> loadDiary(event.diaryId, event.token)
            is DetailsUiEvent.UploadPhotos -> uploadPhotos(event.userId, event.diaryId, event.photos, event.context)
            is DetailsUiEvent.DeleteDiary -> deleteDiary(event.diaryId, event.token)
        }
    }

//    private fun deleteDiary(diaryId: String, token: String) {
//        viewModelScope.launch {
//            try {
//                val result = TravelDiaryRemoteDataSource.deleteDiary(diaryId, token)
//                if (result.isSuccess) {
//                    _uiState.value = _uiState.value.copy(isDeleted = true)
//                } else {
//                    throw Exception("Erro ao deletar diário")
//                }
//            } catch (e: Exception) {
//                _uiState.value = _uiState.value.copy(errorMessage = e.message)
//            }
//        }
//    }

    private fun deleteDiary(diaryId: String, token: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isDeleted = false, isLoading = true) }

                val result = TravelDiaryRemoteDataSource.deleteDiary(diaryId, token)
                if (result.isSuccess) {
                   _uiState.update { it.copy(isDeleted = true, isLoading = false) }
                } else {
                    throw Exception("Erro ao deletar diário")
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Erro ao deletar diário") }
            }
        }
    }

    private fun uploadPhotos(userId: String, diaryId: String, photos: List<Uri>, context: Context) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isUploading = true)
            try {
                val result = TravelDiaryRemoteDataSource.uploadPhotos(userId, diaryId, photos, context)

                if (result.isSuccess) {
                    _uiState.value = _uiState.value.copy(
                        images = _uiState.value.images + photos.map { it.toString() }, // Aqui você pode atualizar com URLs reais se necessário
                        isUploading = false
                    )
                } else {
                    throw Exception("Falha ao fazer upload das imagens")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isUploading = false, errorMessage = e.message)
            }
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
                        id = response.id,
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

    fun resetDeleteState() {
        _uiState.update { it.copy(isDeleted = false) }
    }
}
