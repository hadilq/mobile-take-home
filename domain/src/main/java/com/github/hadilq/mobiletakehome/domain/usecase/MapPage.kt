package com.github.hadilq.mobiletakehome.domain.usecase

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import io.reactivex.Flowable

interface MapPage {

    /**
     * Returns a stream to notify when database is ready. True is ready.
     */
    fun isDatabaseReady(): Flowable<Boolean>

    /**
     * Returns a stream of airports with [airports]' IATA.
     */
    fun loadAirports(airports: Array<String>): Flowable<Airport>
}