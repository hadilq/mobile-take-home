package com.github.hadilq.mobiletakehome.domain.entity

data class Airport(
    val name: String,
    val city: String,
    val country: String,
    val iata: String,
    val latitude: Double,
    val longitude: Double
)