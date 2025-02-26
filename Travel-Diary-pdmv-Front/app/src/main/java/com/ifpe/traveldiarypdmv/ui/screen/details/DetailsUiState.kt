package com.ifpe.traveldiarypdmv.ui.screen.details

data class DetailsUiState(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val city: String = "",
    val state: String = "",
    val images: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isUploading: Boolean = false,
    val errorMessage: String? = null,
    val isUpdated: Boolean = false,
    val isDeleted: Boolean = false
)
