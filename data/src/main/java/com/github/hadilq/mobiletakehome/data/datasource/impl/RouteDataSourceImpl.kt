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

import com.github.hadilq.mobiletakehome.data.datasource.RouteDataSource
import com.github.hadilq.mobiletakehome.data.datasource.map
import com.github.hadilq.mobiletakehome.data.db.dao.RouteDao
import com.github.hadilq.mobiletakehome.data.db.table.RouteWithAirlineAndAirports
import com.github.hadilq.mobiletakehome.domain.entity.Route
import io.reactivex.Flowable

class RouteDataSourceImpl(
    private val dao: RouteDao
) : RouteDataSource {

    override fun findAllRoutesFromOrigin(originId: String): Flowable<Route> =
        dao.findAllRoutesFromOrigin(originId).map {
            it.map(RouteWithAirlineAndAirports::map)
        }.toFlowable().flatMap { Flowable.fromIterable(it) }
}