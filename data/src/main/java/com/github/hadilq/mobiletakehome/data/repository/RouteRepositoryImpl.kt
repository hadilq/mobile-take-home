package com.github.hadilq.mobiletakehome.data.repository

import com.github.hadilq.mobiletakehome.data.datasource.RouteDataSource
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.entity.Route
import com.github.hadilq.mobiletakehome.domain.repository.RouteRepository
import io.reactivex.Flowable
import java.util.concurrent.atomic.AtomicBoolean

class RouteRepositoryImpl(
    private val routeSource: RouteDataSource
) : RouteRepository {

    override fun findShortestRoutes(origin: Airport, destination: Airport): Flowable<Route> {
        val searchTerminated = AtomicBoolean(false)
        return shortestRoutes(origin, destination, ArrayList(), 0, searchTerminated)
            .doOnTerminate { searchTerminated.set(true) }
    }

    private fun shortestRoutes(
        origin: Airport,
        destination: Airport,
        walkedList: List<Route>,
        depth: Int,
        searchTerminated: AtomicBoolean
    ): Flowable<Route> {
        if (depth > 3 || searchTerminated.get()) {
            return Flowable.empty()
        }
        val source = routeSource.findAllRoutesFromOrigin(origin.iata).share()
        return Flowable.merge(
            source
                .filter { it.destination.iata == destination.iata }
                .flatMap {
                    searchTerminated.set(true)
                    Flowable.fromIterable(ArrayList<Route>(walkedList).apply { add(it) })
                },
            source.filter { it.destination.iata != destination.iata }
                .flatMap { route ->
                    if (searchTerminated.get()) {
                        Flowable.empty()
                    } else {
                        shortestRoutes(
                            route.destination,
                            destination,
                            ArrayList<Route>(walkedList).apply { add(route) },
                            depth + 1,
                            searchTerminated
                        )
                    }
                }
        )
    }
}