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
package com.github.hadilq.mobiletakehome.presentationmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.usecase.MapPage
import com.github.hadilq.mobiletakehome.presentationcommon.BaseViewModel
import io.reactivex.processors.PublishProcessor
import org.osmdroid.views.overlay.Polyline
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val mapPage: MapPage
) : BaseViewModel() {

    private val airportsStream = PublishProcessor.create<Array<String>>()

    private val _airportsLiveData by lazy {
        MutableLiveData<List<Airport>>()
    }
    private val _loadingLiveData by lazy {
        MutableLiveData<Boolean>()
    }
    private val _polylineLiveData by lazy {
        MutableLiveData<Polyline>()
    }
    val airportsLiveData: LiveData<List<Airport>> = _airportsLiveData
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData
    val polylineLiveData: LiveData<Polyline> = _polylineLiveData

    init {
        airportsStream
            .switchMap {
                _airportsLiveData.value = null
                mapPage.loadAirports(it)
            }.subscribe { airports ->
                _airportsLiveData.postValue(airports)
            }.track()
    }

    fun checkDatabase() {
        mapPage.isDatabaseReady().subscribe { _loadingLiveData.postValue(!it) }.track()
    }

    fun loadAirports(airports: Array<String>) {
        airportsStream.onNext(airports)
    }

    fun keepLine(line: Polyline) {
        _polylineLiveData.value = line
    }
}