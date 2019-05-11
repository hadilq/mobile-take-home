package com.github.hadilq.mobiletakehome.data.datasource

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import io.reactivex.Flowable

interface AirportDataSource {

    /**
     * Returns a stream of airport that their name is alike to [name].
     */
    fun findAirportByName(name: String): Flowable<Airport>

    /**
     * Returns a stream of airport that their iata is alike to [iata].
     */
    fun findAirportByIata(iata: String): Flowable<Airport>

    /**
     * Returns a stream of airports with [airports]' IATA.
     */
    fun loadAirports(airports: Array<String>): Flowable<Airport>
}