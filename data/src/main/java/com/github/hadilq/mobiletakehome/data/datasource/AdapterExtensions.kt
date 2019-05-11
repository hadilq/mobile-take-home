package com.github.hadilq.mobiletakehome.data.datasource

import com.github.hadilq.mobiletakehome.data.db.table.AirlineRow
import com.github.hadilq.mobiletakehome.data.db.table.AirportRow
import com.github.hadilq.mobiletakehome.data.db.table.RouteWithAirlineAndAirports
import com.github.hadilq.mobiletakehome.domain.entity.Airline
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.entity.Route

fun AirlineRow.map() = Airline(
    name = name,
    twoDigitCode = twoDigitCode,
    threeDigitCode = threeDigitCode,
    country = country
)

fun AirportRow.map() = Airport(
    name = name,
    city = city,
    country = country,
    iata = iata,
    latitude = latitude,
    longitude = longitude
)

fun RouteWithAirlineAndAirports.map() = Route(
    airline = airline.map(),
    origin = origin.map(),
    destination = destination.map()
)