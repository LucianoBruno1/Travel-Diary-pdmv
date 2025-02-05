package com.ifpe.traveldiarypdmv.ui.screen.map

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ifpe.traveldiarypdmv.ui.component.map.TravelDiaryGoogleMap

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    userId: String,
    viewModel: MapViewModel = viewModel()
) {
    val mapPoints by viewModel.mapPoints
    val errorMessage by viewModel.errorMessage

    LaunchedEffect(Unit) {
        viewModel.loadMapPoints(userId)
    }

    if (errorMessage != null) {
        Text(
            text = errorMessage ?: "Erro desconhecido",
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        TravelDiaryGoogleMap(
            modifier = modifier,
            markerLocations = mapPoints
        )
    }
}