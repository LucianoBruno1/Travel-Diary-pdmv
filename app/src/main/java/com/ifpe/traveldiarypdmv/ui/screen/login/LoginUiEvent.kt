package com.ifpe.traveldiarypdmv.ui.screen.login

sealed class LoginUiEvent {
    data class OnLoginClicked(val email: String, val password: String) : LoginUiEvent()
    object OnResetError : LoginUiEvent()
}