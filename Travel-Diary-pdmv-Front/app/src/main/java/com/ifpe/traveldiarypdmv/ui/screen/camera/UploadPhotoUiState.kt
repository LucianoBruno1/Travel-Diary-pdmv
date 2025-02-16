package com.ifpe.traveldiarypdmv.ui.screen.camera

data class UploadPhotoUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)