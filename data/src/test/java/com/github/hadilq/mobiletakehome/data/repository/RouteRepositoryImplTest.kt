package com.github.hadilq.mobiletakehome.data.repository

import com.github.hadilq.mobiletakehome.data.datasource.RouteDataSource
import com.github.hadilq.mobiletakehome.domain.entity.Airline
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.entity.Route
import com.github.hadilq.mobiletakehome.domain.repository.RouteRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Flowable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

class RouteRepositoryImplTest {

    private lateinit var repository: RouteRepository
    private lateinit var datasource: RouteDataSource

    @Before
    fun setup() {
        datasource = mock()
        repository = RouteRepositoryImpl(datasource)

        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun close() {
        RxJavaPlugins.reset()
    }

    @Test
    fun findSimpleRoute() {
        val origin = Airport("A", "A", "A", "A", 0.0, 0.0)
        val destination = Airport("B", "B", "B", "B", 1.0, 1.0)
        val route = Route(
            Airline("s", "RF", "ERT", "ED"),
            origin,
            destination
        )
        `when`(datasource.findAllRoutesFromOrigin(any())).doReturn(Flowable.just(route))

        val test = repository.findShortestRoutes(origin, destination).test()

        test.assertComplete()
        test.assertNoErrors()
        assertTrue(1 == test.valueCount())
        assertTrue(route == test.values()[0])
    }

    @Test
    fun findTwoPointRoute() {
        val a1 = Airport("A1", "A1", "A1", "A1", 0.0, 0.0)
        val a2 = Airport("A2", "A2", "A2", "A2", 1.0, 1.0)
        val a3 = Airport("A3", "A3", "A3", "A3", 2.0, 2.0)
        val r1 = Route(Airline("r1", "RF", "ERT", "ED"), a1, a2)
        val r2 = Route(Airline("r2", "RF", "ERT", "ED"), a2, a3)
        `when`(datasource.findAllRoutesFromOrigin(any())).thenAnswer {
            when (it.getArgument<String>(0)) {
                "A1" -> Flowable.fromIterable(ArrayList<Route>().apply {
                    add(r1)
                })
                "A2" -> Flowable.fromIterable(ArrayList<Route>().apply {
                    add(r2)
                })
                else -> Flowable.empty()
            }
        }

        val test = repository.findShortestRoutes(a1, a3).test()

        test.assertComplete()
        test.assertNoErrors()
        assertTrue(2 == test.valueCount())
        assertTrue(r1 == test.values()[0])
        assertTrue(r2 == test.values()[1])
    }
}