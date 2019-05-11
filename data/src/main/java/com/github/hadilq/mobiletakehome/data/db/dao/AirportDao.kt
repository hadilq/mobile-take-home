package com.github.hadilq.mobiletakehome.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.hadilq.mobiletakehome.data.db.table.AirportRow

@Dao
interface AirportDao {

    @Query("SELECT * FROM airport")
    fun all(): List<AirportRow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg airport: AirportRow)
}