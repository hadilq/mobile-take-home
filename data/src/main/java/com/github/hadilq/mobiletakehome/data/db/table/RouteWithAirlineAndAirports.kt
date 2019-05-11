package com.github.hadilq.mobiletakehome.data.db.table

import androidx.room.Embedded

class RouteWithAirlineAndAirports(
    @Embedded(prefix = "airline_") val airline: AirlineRow,
    @Embedded(prefix = "origin_") val origin: AirportRow,
    @Embedded(prefix = "destination_") val destination: AirportRow
)