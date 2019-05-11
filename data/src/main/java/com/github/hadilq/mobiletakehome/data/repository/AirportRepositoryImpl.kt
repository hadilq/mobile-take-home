package com.github.hadilq.mobiletakehome.data.repository

import com.github.hadilq.mobiletakehome.data.datasource.AirportDataSource
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.repository.AirportRepository
import io.reactivex.Flowable

class AirportRepositoryImpl(
    private val airportSource: AirportDataSource
) : AirportRepository {

    override fun checkAirport(iataOrName: String): Flowable<Airport> =
        Flowable.merge(airportSource.findAirportByName(iataOrName), airportSource.findAirportByIata(iataOrName))

    override fun loadAirports(airports: Array<String>): Flowable<Airport> =
        airportSource.loadAirports(airports)
}