package com.github.hadilq.mobiletakehome.domain.usecase.impl

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.repository.RouteRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test

class GetPathImplTest {

    private lateinit var repository: RouteRepository

    @Before
    fun setup() {
        repository = mock()
    }

    @Test
    fun getShortestPath() {
        // Given
        val usecase = GetPathImpl(repository)
        val origin = Airport("skfv", "dfv", "AKN", "QID", 54.109, 39.30)
        val destination = Airport("ls", "drer", "PAE", "EVN", 52.109, 19.30)

        // When
        usecase.getShortestPath(origin, destination)

        // Then
        verify(repository).findShortestRoutes(origin, destination)
    }
}