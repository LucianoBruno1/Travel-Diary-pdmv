package com.ifpe.traveldiarypdmv.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val id: Int,
    val name: String,
    val email: String
)
