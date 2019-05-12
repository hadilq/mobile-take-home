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
package com.github.hadilq.mobiletakehome.data.datasource.impl

import com.github.hadilq.mobiletakehome.data.datasource.AirportDataSource
import com.github.hadilq.mobiletakehome.data.datasource.map
import com.github.hadilq.mobiletakehome.data.db.dao.AirportDao
import com.github.hadilq.mobiletakehome.data.db.table.AirportRow
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import io.reactivex.Flowable

class AirportDataSourceImpl(
    private val dao: AirportDao
) : AirportDataSource {

    override fun findAirportByName(name: String): Flowable<Airport> =
        dao.findAirportByName("%$name%").map(AirportRow::map)

    override fun findAirportByIata(iata: String): Flowable<Airport> =
        dao.findAirportByIata("%$iata%").map(AirportRow::map)

    override fun loadAirports(airports: Array<String>): Flowable<List<Airport>> =
        Flowable.fromIterable(airports.asList()).flatMap {
            dao.loadAirports(it).toFlowable().map(AirportRow::map)
        }.toList().toFlowable()
}