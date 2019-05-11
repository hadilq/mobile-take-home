package com.github.hadilq.mobiletakehome.data.utils

import com.github.hadilq.mobiletakehome.data.db.table.AirlineRow
import com.github.hadilq.mobiletakehome.data.db.table.AirportRow
import com.github.hadilq.mobiletakehome.data.db.table.RouteRow

interface CsvReader {
    fun loadAirlines(): Map<String, AirlineRow>

    fun loadAirports(): Map<String, AirportRow>

    fun loadRouts(): List<RouteRow>
}