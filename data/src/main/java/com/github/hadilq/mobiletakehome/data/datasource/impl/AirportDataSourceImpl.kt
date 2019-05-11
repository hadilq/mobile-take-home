package com.github.hadilq.mobiletakehome.data.datasource.impl

import com.github.hadilq.mobiletakehome.data.datasource.AirportDataSource
import com.github.hadilq.mobiletakehome.data.datasource.map
import com.github.hadilq.mobiletakehome.data.db.dao.AirportDao
import com.github.hadilq.mobiletakehome.data.db.table.AirportRow
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import io.reactivex.Flowable

class AirportDataSourceImpl(
    private val dao: AirportDao
) : AirportDataSource {

    override fun findAirportByName(name: String): Flowable<Airport> =
        dao.findAirportByName("%$name%").map(AirportRow::map)

    override fun findAirportByIata(iata: String): Flowable<Airport> =
        dao.findAirportByIata("%$iata%").map(AirportRow::map)
}