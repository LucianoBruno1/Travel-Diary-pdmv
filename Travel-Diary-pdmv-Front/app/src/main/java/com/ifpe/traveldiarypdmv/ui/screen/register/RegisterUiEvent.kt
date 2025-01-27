package com.ifpe.traveldiarypdmv.ui.screen.register

sealed class RegisterUiEvent {
    data class OnRegisterClicked(val name: String, val email: String, val password: String) : RegisterUiEvent()
    object OnResetError : RegisterUiEvent()
}