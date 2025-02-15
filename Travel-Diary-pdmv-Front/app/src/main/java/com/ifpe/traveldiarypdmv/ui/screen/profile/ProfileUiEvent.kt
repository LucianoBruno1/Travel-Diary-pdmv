package com.ifpe.traveldiarypdmv.ui.screen.profile

sealed class ProfileUiEvent {
    data class LoadProfile(val userId: String, val token: String) : ProfileUiEvent()
}