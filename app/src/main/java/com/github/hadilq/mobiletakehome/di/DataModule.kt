package com.github.hadilq.mobiletakehome.di

import com.github.hadilq.mobiletakehome.data.datasource.AirportDataSource
import com.github.hadilq.mobiletakehome.data.datasource.RouteDataSource
import com.github.hadilq.mobiletakehome.data.datasource.impl.AirportDataSourceImpl
import com.github.hadilq.mobiletakehome.data.datasource.impl.RouteDataSourceImpl
import com.github.hadilq.mobiletakehome.data.db.dao.AirportDao
import com.github.hadilq.mobiletakehome.data.db.dao.RouteDao
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideAirportDataSource(dao: AirportDao): AirportDataSource = AirportDataSourceImpl(dao)

    @Provides
    fun provideRouteDataSource(dao: RouteDao): RouteDataSource = RouteDataSourceImpl(dao)
}