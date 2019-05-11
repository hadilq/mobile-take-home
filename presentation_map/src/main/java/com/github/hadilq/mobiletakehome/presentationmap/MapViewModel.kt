package com.github.hadilq.mobiletakehome.presentationmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.usecase.MapPage
import com.github.hadilq.mobiletakehome.presentationcommon.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val mapPage: MapPage
) : BaseViewModel() {

    private val _airportsLiveData by lazy {
        MutableLiveData<ArrayList<Airport>>()
    }
    private val _loadingLiveData by lazy {
        MutableLiveData<Boolean>()
    }
    val airportsLiveData: LiveData<out List<Airport>> = _airportsLiveData
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    fun checkDatabase() {
        mapPage.isDatabaseReady().subscribe { _loadingLiveData.postValue(it) }.track()
    }

    fun loadAirports(airports: Array<String>) {
        mapPage.loadAirports(airports).subscribe { airport ->
            _airportsLiveData.value?.apply {
                add(airport)
                _airportsLiveData.value = this
            } ?: let {
                _airportsLiveData.value = ArrayList<Airport>().apply { add(airport) }
            }
        }.track()
    }
}