/***
 * Copyright 2019 Hadi Lashkari Ghouchani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * */
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