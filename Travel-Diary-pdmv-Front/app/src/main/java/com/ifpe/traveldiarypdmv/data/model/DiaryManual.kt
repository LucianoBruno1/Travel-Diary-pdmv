    package com.ifpe.traveldiarypdmv.data.model

    data class DiaryManual(
        val name: String,
        val description: String?,
        val latitude: Double, // Alterado para Double
        val longitude: Double, // Alterado para Double
        val id: String
    )
