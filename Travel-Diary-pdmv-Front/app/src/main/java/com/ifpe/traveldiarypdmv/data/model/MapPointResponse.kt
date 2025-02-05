package com.ifpe.traveldiarypdmv.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MapPointResponse(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val diary: DiaryInfo
)
