package com.ifpe.traveldiarypdmv.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DiaryDetailsResponse(
    val id: String,
    val name: String,
    val description: String?,
    val city: String,
    val state: String,
    val photos: List<Photo>
) {
    @Serializable
    data class Photo(
        val id: String,
        val file_path: String
    )
}