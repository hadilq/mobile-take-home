/***
 * Copyright 2019 Hadi Lashkari Ghouchani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * */
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