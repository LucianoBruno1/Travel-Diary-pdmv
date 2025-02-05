package com.ifpe.traveldiarypdmv.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DiaryInfo(
    val id: String,
    val name: String
)
