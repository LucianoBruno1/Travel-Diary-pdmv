package com.ifpe.traveldiarypdmv.ui.screen.profile

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val birthDate: String? = null,
    val profilePicture: String? = null,
    val bio: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)