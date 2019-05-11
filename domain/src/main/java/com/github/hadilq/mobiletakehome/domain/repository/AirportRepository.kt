package com.github.hadilq.mobiletakehome.domain.repository

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import io.reactivex.Flowable

interface AirportRepository {

    /**
     * Returns a stream of possible airports with [iataOrName].
     */
    fun checkAirport(iataOrName: String): Flowable<Airport>
}