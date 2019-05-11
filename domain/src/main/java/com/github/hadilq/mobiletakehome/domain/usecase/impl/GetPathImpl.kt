package com.github.hadilq.mobiletakehome.domain.usecase.impl

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.entity.Route
import com.github.hadilq.mobiletakehome.domain.repository.RouteRepository
import com.github.hadilq.mobiletakehome.domain.usecase.GetPath
import io.reactivex.Flowable

class GetPathImpl(private val repository: RouteRepository) : GetPath {

    override fun getShortestPath(origin: Airport, destination: Airport): Flowable<Route> =
        repository.findShortestRoutes(origin, destination)
}