package com.github.hadilq.mobiletakehome.domain.repository

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.entity.Route
import io.reactivex.Flowable

interface RouteRepository {

    /**
     * Returns a stream of Routes that connects the origin to the destination with the least transfer(ie. it will take
     * the same amount of time to travel between two airports, regardless of the physical distance between them).
     */
    fun findShortestRoutes(origin: Airport, destination: Airport): Flowable<List<Route>>
}