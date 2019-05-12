package com.github.hadilq.mobiletakehome.data.repository

import com.github.hadilq.mobiletakehome.data.datasource.RouteDataSource
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.entity.Route
import com.github.hadilq.mobiletakehome.domain.repository.RouteRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicBoolean

class RouteRepositoryImpl(
    private val routeSource: RouteDataSource
) : RouteRepository {

    override fun findShortestRoutes(origin: Airport, destination: Airport): Flowable<List<Route>> {
        val searchTerminated = AtomicBoolean(false)
        return if (origin.iata == destination.iata) {
            Flowable.empty()
        } else {
            shortestRoutes(origin, destination, ArrayList(), 0, searchTerminated)
                .doOnTerminate { searchTerminated.set(true) }
        }
    }

    private fun shortestRoutes(
        origin: Airport,
        destination: Airport,
        walkedList: List<Route>,
        depth: Int,
        searchTerminated: AtomicBoolean
    ): Flowable<List<Route>> {
        if (depth > 2 || searchTerminated.get()) {
            return Flowable.empty()
        }
        val source =
            routeSource.findAllRoutesFromOrigin(origin.iata).toFlowable().flatMap { Flowable.fromIterable(it) }.share()
        return Flowable.merge(
            source
                .filter { it.destination.iata == destination.iata }
                .flatMap {
                    if (searchTerminated.get()) {
                        Flowable.empty()
                    } else {
                        searchTerminated.set(true)
                        Flowable.just(ArrayList<Route>(walkedList).apply { add(it) })
                    }
                },
            source.filter { it.destination.iata != destination.iata }
                .subscribeOn(Schedulers.computation())
                .flatMap { route ->
                    if (searchTerminated.get()) {
                        Flowable.empty<List<Route>>()
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