package com.ifpe.traveldiarypdmv.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val id: String,
    @SerialName("created_at") val createdAt: String, // Mapeia "created_at" para "createdAt"
    val name: String,
    val email: String
)
