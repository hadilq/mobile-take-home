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
        dao.findAllRoutesFromOrigin(originId).map(RouteWithAirlineAndAirports::map)
}