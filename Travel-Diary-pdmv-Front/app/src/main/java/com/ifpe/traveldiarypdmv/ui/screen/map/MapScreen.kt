package com.ifpe.traveldiarypdmv.ui.screen.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ifpe.traveldiarypdmv.ui.component.map.TravelDiaryGoogleMap

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    TravelDiaryGoogleMap(modifier = modifier)
}