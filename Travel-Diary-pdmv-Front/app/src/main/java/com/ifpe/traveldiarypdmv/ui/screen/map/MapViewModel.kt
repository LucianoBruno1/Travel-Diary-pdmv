package com.ifpe.traveldiarypdmv.ui.screen.map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.ifpe.traveldiarypdmv.core.network.TravelDiaryRemoteDataSource
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val _mapPoints = mutableStateOf(emptyList<LatLng>())
    val mapPoints: State<List<LatLng>> = _mapPoints

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun loadMapPoints(userId: String) {
        viewModelScope.launch {
            val result = TravelDiaryRemoteDataSource.getMapPoints(userId)
            result.onSuccess { points ->
                _mapPoints.value = points.map { LatLng(it.latitude, it.longitude) }
            }.onFailure { error ->
                _errorMessage.value = error.message
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

