package com.github.hadilq.mobiletakehome.domain.usecase.impl

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.entity.Route
import com.github.hadilq.mobiletakehome.domain.repository.AirportRepository
import com.github.hadilq.mobiletakehome.domain.repository.RouteRepository
import com.github.hadilq.mobiletakehome.domain.usecase.GetPath
import io.reactivex.Flowable

class GetPathImpl(
    private val airportRepository: AirportRepository,
    private val routeRepository: RouteRepository
) : GetPath {

    override fun checkAirport(iataOrName: String): Flowable<Airport> = airportRepository.checkAirport(iataOrName)

    override fun getShortestPath(origin: Airport, destination: Airport): Flowable<Route> =
        routeRepository.findShortestRoutes(origin, destination)
}