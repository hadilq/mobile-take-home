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
package com.github.hadilq.mobiletakehome.di.app

import android.app.Application
import android.content.Context
import com.github.hadilq.mobiletakehome.data.db.AppDatabase
import com.github.hadilq.mobiletakehome.data.db.dao.AirlineDao
import com.github.hadilq.mobiletakehome.data.db.dao.AirportDao
import com.github.hadilq.mobiletakehome.data.db.dao.RouteDao
import com.github.hadilq.mobiletakehome.data.utils.CsvReader
import com.github.hadilq.mobiletakehome.data.utils.impl.CsvReaderImpl
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application, csvReader: CsvReader): AppDatabase {
        AppDatabase.setContext(app)
        AppDatabase.csvReader = csvReader
        AppDatabase.executor = Executors.newSingleThreadExecutor()
        return AppDatabase.sInstance
    }

    @Singleton
    @Provides
    fun provideCsvReader(context: Context): CsvReader = CsvReaderImpl(context)

    @Singleton
    @Provides
    fun provideAirlineDao(database: AppDatabase): AirlineDao = database.airlineDao()

    @Singleton
    @Provides
    fun provideAirportDao(database: AppDatabase): AirportDao = database.airportDao()

    @Singleton
    @Provides
    fun provideRouteDao(database: AppDatabase): RouteDao = database.routeDao()
}