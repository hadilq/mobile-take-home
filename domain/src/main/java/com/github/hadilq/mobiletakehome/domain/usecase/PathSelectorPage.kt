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
package com.github.hadilq.mobiletakehome.domain.usecase

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.entity.Route
import io.reactivex.Flowable

interface PathSelectorPage {

    /**
     * Returns a stream of possible airports with [iataOrName].
     */
    fun checkAirport(iataOrName: String): Flowable<Airport>

    /**
     * Returns a list of Routes that connects the origin to the destination with the least transfer(ie. it will take
     * the same amount of time to travel between two airports, regardless of the physical distance between them).
     */
    fun getShortestPath(origin: Airport, destination: Airport): Flowable<List<Route>>
}