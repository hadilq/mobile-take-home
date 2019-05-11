package com.github.hadilq.mobiletakehome.data.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class AirportRow(
    val name: String,
    val city: String,
    val country: String,
    @PrimaryKey val iata: String,
    val latitude: Double,
    val longitude: Double
)