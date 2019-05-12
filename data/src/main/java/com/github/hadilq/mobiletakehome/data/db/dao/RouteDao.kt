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
import com.github.hadilq.mobiletakehome.data.db.table.RouteRow
import com.github.hadilq.mobiletakehome.data.db.table.RouteWithAirlineAndAirports
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface RouteDao {

    @Query("SELECT * FROM route")
    fun all(): Flowable<RouteRow>

    @Query("SELECT al.name AS airline_name, al.twoDigitCode AS airline_twoDigitCode, al.threeDigitCode AS airline_threeDigitCode, al.country AS airline_country, ap1.name AS origin_name, ap1.city AS origin_city, ap1.country AS origin_country, ap1.iata AS origin_iata, ap1.latitude AS origin_latitude, ap1.longitude AS origin_longitude, ap2.name AS destination_name, ap2.city AS destination_city,ap2.country AS destination_country, ap2.iata AS destination_iata, ap2. latitude AS destination_latitude, ap2.longitude AS destination_longitude FROM route AS r INNER JOIN airline AS al ON r.airlineId = al.twoDigitCode INNER JOIN airport AS ap2 ON r.destinationId = ap2.iata INNER JOIN airport AS ap1 ON r.originId = ap1.iata WHERE r.originId = :originId")
    fun findAllRoutesFromOrigin(originId: String): Maybe<List<RouteWithAirlineAndAirports>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg route: RouteRow)
}