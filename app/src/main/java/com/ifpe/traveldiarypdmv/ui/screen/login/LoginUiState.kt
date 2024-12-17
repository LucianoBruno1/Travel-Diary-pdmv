package com.ifpe.traveldiarypdmv.ui.screen.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false,
    val token: String? = null
)
