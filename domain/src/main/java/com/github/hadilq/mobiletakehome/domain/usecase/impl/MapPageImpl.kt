package com.github.hadilq.mobiletakehome.domain.usecase.impl

import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.repository.AirportRepository
import com.github.hadilq.mobiletakehome.domain.repository.DatabaseReadyRepository
import com.github.hadilq.mobiletakehome.domain.usecase.MapPage
import io.reactivex.Flowable

class MapPageImpl(
    private val readyRepository: DatabaseReadyRepository,
    private val airportRepository: AirportRepository
) : MapPage {

    override fun isDatabaseReady(): Flowable<Boolean> = readyRepository.isDatabaseReady()

    override fun loadAirports(airports: Array<String>): Flowable<Airport> = airportRepository.loadAirports(airports)
}