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
package com.github.hadilq.mobiletakehome.data.repository

import com.github.hadilq.mobiletakehome.data.datasource.AirportDataSource
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.repository.AirportRepository
import io.reactivex.Flowable

class AirportRepositoryImpl(
    private val airportSource: AirportDataSource
) : AirportRepository {

    override fun checkAirport(iataOrName: String): Flowable<Airport> =
        Flowable.merge(airportSource.findAirportByName(iataOrName), airportSource.findAirportByIata(iataOrName))

    override fun loadAirports(airports: Array<String>): Flowable<List<Airport>> =
        airportSource.loadAirports(airports)
}