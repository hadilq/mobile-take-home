package com.github.hadilq.mobiletakehome.data.utils.impl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import com.github.hadilq.mobiletakehome.data.utils.CsvReader
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CsvReaderImplTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var csvReader: CsvReader

    @Before
    fun setup() {
        csvReader = CsvReaderImpl(InstrumentationRegistry.getContext())
    }

    @Test
    fun airlineAssetLoading() {
        val airlines = csvReader.loadAirlines()
        assertFalse(airlines.isEmpty())
    }

    @Test
    fun airportAssetLoading() {
        val airports = csvReader.loadAirports()
        assertFalse(airports.isEmpty())
    }

    @Test
    fun routesAssetLoading() {
        val routes = csvReader.loadRouts()
        assertFalse(routes.isEmpty())
    }

    @Test
    fun routesATLtoDEN() {
        val routes = csvReader.loadRouts()

        assertTrue(routes.any { it.originId == "ATL" && it.destinationId == "DEN" })
    }

    @Test
    fun routesBJMtoNBO() {
        val routes = csvReader.loadRouts()

        assertTrue(routes.any { it.originId == "BJM" && it.destinationId == "NBO" })
    }

}