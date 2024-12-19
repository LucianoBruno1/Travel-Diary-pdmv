package com.ifpe.traveldiarypdmv.ui.screen.register

data class RegisterUiState (
    val isLoading: Boolean = false, 
    val errorMessage: String? = null,
    val isRegistered: Boolean = false
)