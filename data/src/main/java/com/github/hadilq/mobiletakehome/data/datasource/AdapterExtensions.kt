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

import com.github.hadilq.mobiletakehome.data.db.table.AirlineRow
import com.github.hadilq.mobiletakehome.data.db.table.AirportRow
import com.github.hadilq.mobiletakehome.data.db.table.RouteWithAirlineAndAirports
import com.github.hadilq.mobiletakehome.domain.entity.Airline
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.entity.Route

fun AirlineRow.map() = Airline(
    name = name,
    twoDigitCode = twoDigitCode,
    threeDigitCode = threeDigitCode,
    country = country
)

fun AirportRow.map() = Airport(
    name = name,
    city = city,
    country = country,
    iata = iata,
    latitude = latitude,
    longitude = longitude
)

fun RouteWithAirlineAndAirports.map() = Route(
    airline = airline.map(),
    origin = origin.map(),
    destination = destination.map()
)