package com.ifpe.traveldiarypdmv.data.model

data class Diary(
    val id: String,
    val name: String,
    val description: String?,
    val travel_date: String,
    val city: String,
    val state: String,
    val isFavorited: Boolean = false,
)
