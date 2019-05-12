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