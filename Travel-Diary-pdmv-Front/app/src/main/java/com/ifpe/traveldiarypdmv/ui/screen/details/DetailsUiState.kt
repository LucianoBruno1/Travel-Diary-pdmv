package com.ifpe.traveldiarypdmv.ui.screen.details

data class DetailsUiState(
    val name: String = "",
    val description: String = "",
    val city: String = "",
    val state: String = "",
    val images: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
