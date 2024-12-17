package com.ifpe.traveldiarypdmv.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val user: User? = null,
    val token: String? = null,
    val message: String? = null
)

