package com.github.hadilq.mobiletakehome.data.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airline")
data class AirlineRow(
    val name: String,
    @PrimaryKey val twoDigitCode: String,
    val threeDigitCode: String,
    val country: String
)