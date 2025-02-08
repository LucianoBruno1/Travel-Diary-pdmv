package com.ifpe.traveldiarypdmv.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val name: String,
    val email: String,
    val birthDate: String? = null,
    val profilePicture: String? = null,
    val bio: String? = null
)