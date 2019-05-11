package com.github.hadilq.mobiletakehome.di

import com.github.hadilq.mobiletakehome.data.datasource.AirportDataSource
import com.github.hadilq.mobiletakehome.data.datasource.RouteDataSource
import com.github.hadilq.mobiletakehome.data.db.AppDatabase
import com.github.hadilq.mobiletakehome.data.repository.AirportRepositoryImpl
import com.github.hadilq.mobiletakehome.data.repository.DatabaseReadyRepositoryImpl
import com.github.hadilq.mobiletakehome.data.repository.RouteRepositoryImpl
import com.github.hadilq.mobiletakehome.domain.repository.AirportRepository
import com.github.hadilq.mobiletakehome.domain.repository.DatabaseReadyRepository
import com.github.hadilq.mobiletakehome.domain.repository.RouteRepository
import com.github.hadilq.mobiletakehome.domain.usecase.MapPage
import com.github.hadilq.mobiletakehome.domain.usecase.PathSelectorPage
import com.github.hadilq.mobiletakehome.domain.usecase.impl.MapPageImpl
import com.github.hadilq.mobiletakehome.domain.usecase.impl.PathSelectorPageImpl
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideDatabaseReadyRepository(appDatabase: AppDatabase): DatabaseReadyRepository =
        DatabaseReadyRepositoryImpl(appDatabase)

    @Provides
    fun provideAirportRepository(dataSource: AirportDataSource): AirportRepository = AirportRepositoryImpl(dataSource)

    @Provides
    fun provideRouteRepository(dataSource: RouteDataSource): RouteRepository = RouteRepositoryImpl(dataSource)

    @Provides
    fun provideMapPage(
        readyRepository: DatabaseReadyRepository,
        airportRepository: AirportRepository
    ): MapPage = MapPageImpl(readyRepository, airportRepository)

    @Provides
    fun providePathSelectorPage(
        airportRepository: AirportRepository,
        routeRepository: RouteRepository
    ): PathSelectorPage = PathSelectorPageImpl(airportRepository, routeRepository)


}