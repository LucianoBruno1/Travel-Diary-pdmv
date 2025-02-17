package com.ifpe.traveldiarypdmv.ui.component.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ifpe.traveldiarypdmv.data.model.MapPointResponse
import kotlinx.coroutines.suspendCancellableCoroutine

@Composable
fun TravelDiaryGoogleMap(
    modifier: Modifier = Modifier,
    markerLocations: List<MapPointResponse> = emptyList(),
    onMarkerClick: (String) -> Unit
) {
    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        userLocation = getCurrentLocation(context)
        userLocation?.let {
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(it, 5f))
        }
    }
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = true)
    ) {
        // Adicionando os marcadores passados como parâmetro
        markerLocations.forEach {  mapPoint ->
            Marker(
                state = MarkerState(position = LatLng(mapPoint.latitude, mapPoint.longitude)),
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
                title = mapPoint.diary.name,
                snippet = "Clique para detalhes",
                onClick = {
                    onMarkerClick(mapPoint.diary.id)
                    true
                }
            )
        }
    }
}

/**
 * Obtém a localização atual do usuário.
 */
@SuppressLint("MissingPermission")
private suspend fun getCurrentLocation(context: Context): LatLng? {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    return suspendCancellableCoroutine { continuation ->
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    continuation.resume(
                        LatLng(
                            location.latitude,
                            location.longitude
                        )
                    ) { cause, _, _ -> null?.let { it(cause) } }
                } else {
                    continuation.resume(null) { cause, _, _ -> null?.let { it(cause) } }
                }
            }
            .addOnFailureListener { exception ->
                println("Erro ao obter localização: ${exception.message}")
                continuation.resume(null) { cause, _, _ -> null?.let { it(cause) } }
            }
    }
}