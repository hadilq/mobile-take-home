package com.github.hadilq.mobiletakehome.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import com.github.hadilq.mobiletakehome.data.utils.CsvReader
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executor

class AppDatabaseTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var csvReader: CsvReader
    private lateinit var executor: Executor

    @Before
    fun setup() {
        csvReader = mock()
        executor = mock()
        AppDatabase.csvReader = csvReader
        AppDatabase.executor = executor
        AppDatabase.setContext(InstrumentationRegistry.getContext())
        database = AppDatabase.sInstance
    }

    @After
    fun close() {
        database.close()
    }

    @Test
    fun loadings() {
        val airlines= database.airlineDao().all()
        val airports= database.airportDao().all()
        val routes = database.routeDao().all()

        assertTrue(airlines.isEmpty())
        assertTrue(airports.isEmpty())
        assertTrue(routes.isEmpty())
        verify(executor).execute(any())
        database.isDatabaseCreated().test().assertValue(false)
    }

}