package com.github.hadilq.mobiletakehome.domain.entity

data class Route(
    val airline: Airline,
    val origin: Airport,
    val destination: Airport
)