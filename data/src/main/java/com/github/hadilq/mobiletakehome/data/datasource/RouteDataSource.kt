package com.github.hadilq.mobiletakehome.data.datasource

import com.github.hadilq.mobiletakehome.domain.entity.Route
import io.reactivex.Flowable

interface RouteDataSource {

    /**
     * Returns a stream of routes where their origin's id is [originId].
     */
    fun findAllRoutesFromOrigin(originId: String): Flowable<Route>
}