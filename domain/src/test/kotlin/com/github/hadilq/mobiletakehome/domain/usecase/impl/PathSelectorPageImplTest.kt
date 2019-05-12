package com.github.hadilq.mobiletakehome.domain.usecase.impl

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.repository.AirportRepository
import com.github.hadilq.mobiletakehome.domain.repository.RouteRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Flowable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

class PathSelectorPageImplTest {

    private lateinit var routeRepository: RouteRepository
    private lateinit var aiRepository: AirportRepository

    @Before
    fun setup() {
        routeRepository = mock()
        aiRepository = mock()

        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun close() {
        RxJavaPlugins.reset()
    }

    @Test
    fun checkAirport() {
        // Given
        `when`(aiRepository.checkAirport(any())).doReturn(Flowable.just(Airport("A", "A", "A", "A", 0.0, 0.0)))
        val usecase = PathSelectorPageImpl(aiRepository, routeRepository)
        val iataOrName = "UEI"

        // When
        usecase.checkAirport(iataOrName)

        // Then
        verify(aiRepository).checkAirport(iataOrName)
    }

    @Test
    fun getShortestPath() {
        // Given
        `when`(routeRepository.findShortestRoutes(any(), any())).doReturn(Flowable.just(ArrayList()))
        val usecase = PathSelectorPageImpl(aiRepository, routeRepository)
        val origin = Airport("skfv", "dfv", "AKN", "QID", 54.109, 39.30)
        val destination = Airport("ls", "drer", "PAE", "EVN", 52.109, 19.30)

        // When
        usecase.getShortestPath(origin, destination)

        // Then
        verify(routeRepository).findShortestRoutes(origin, destination)
    }
}