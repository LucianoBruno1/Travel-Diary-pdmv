package com.ifpe.traveldiarypdmv.data.model

data class Diary(
    val name: String,
    val description: String?,
    val travel_date: String,
    val city: String,
    val state: String,
    val latitude: String,
    val longitude: String,
    val userId: String,
    val id: String,
    val isFavorited: Boolean = false,
)