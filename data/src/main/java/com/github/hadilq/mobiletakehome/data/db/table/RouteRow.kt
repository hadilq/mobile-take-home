package com.github.hadilq.mobiletakehome.data.db.table

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "route")
data class RouteRow(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val airlineId: String,
    val originId: String,
    val destinationId: String
)