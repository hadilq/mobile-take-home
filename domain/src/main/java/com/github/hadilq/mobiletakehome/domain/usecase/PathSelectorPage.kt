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