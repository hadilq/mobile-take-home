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
package com.github.hadilq.mobiletakehome.data.datasource

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import io.reactivex.Flowable

interface AirportDataSource {

    /**
     * Returns a stream of airport that their name is alike to [name].
     */
    fun findAirportByName(name: String): Flowable<Airport>

    /**
     * Returns a stream of airport that their iata is alike to [iata].
     */
    fun findAirportByIata(iata: String): Flowable<Airport>

    /**
     * Returns a stream of airports with [airports]' IATA.
     */
    fun loadAirports(airports: Array<String>): Flowable<List<Airport>>
}