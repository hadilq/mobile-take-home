package com.github.hadilq.mobiletakehome.domain.usecase.impl

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.repository.AirportRepository
import com.github.hadilq.mobiletakehome.domain.repository.RouteRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class PathSelectorPageImplTest {

    private lateinit var routeRepository: RouteRepository
    private lateinit var aiRepository: AirportRepository

    @Before
    fun setup() {
        routeRepository = mock()
        aiRepository = mock()
    }

    @Test
    fun checkAirport() {
        // Given
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
        val usecase = PathSelectorPageImpl(aiRepository, routeRepository)
        val origin = Airport("skfv", "dfv", "AKN", "QID", 54.109, 39.30)
        val destination = Airport("ls", "drer", "PAE", "EVN", 52.109, 19.30)

        // When
        usecase.getShortestPath(origin, destination)

        // Then
        verify(routeRepository).findShortestRoutes(origin, destination)
    }
}