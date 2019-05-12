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