package com.github.hadilq.mobiletakehome.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.hadilq.mobiletakehome.data.db.table.AirportRow
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface AirportDao {

    @Query("SELECT * FROM airport")
    fun all(): Flowable<AirportRow>

    @Query("SELECT * FROM airport WHERE name LIKE :name")
    fun findAirportByName(name: String): Flowable<AirportRow>

    @Query("SELECT * FROM airport WHERE iata LIKE :iata")
    fun findAirportByIata(iata: String): Flowable<AirportRow>

    @Query("SELECT * FROM airport WHERE iata = :airportId")
    fun loadAirports(airportId: String): Maybe<AirportRow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg airport: AirportRow)
}