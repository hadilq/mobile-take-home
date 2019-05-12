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
package com.github.hadilq.mobiletakehome.domain.usecase.impl

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.entity.Route
import com.github.hadilq.mobiletakehome.domain.repository.AirportRepository
import com.github.hadilq.mobiletakehome.domain.repository.RouteRepository
import com.github.hadilq.mobiletakehome.domain.usecase.PathSelectorPage
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class PathSelectorPageImpl(
    private val airportRepository: AirportRepository,
    private val routeRepository: RouteRepository
) : PathSelectorPage {

    override fun checkAirport(iataOrName: String): Flowable<Airport> =
        airportRepository.checkAirport(iataOrName).subscribeOn(Schedulers.io())

    override fun getShortestPath(origin: Airport, destination: Airport): Flowable<List<Route>> =
        routeRepository.findShortestRoutes(origin, destination).subscribeOn(Schedulers.io())
}