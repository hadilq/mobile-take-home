package com.github.hadilq.mobiletakehome.presentationpathselector

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.entity.Route
import com.github.hadilq.mobiletakehome.domain.usecase.PathSelectorPage
import com.github.hadilq.mobiletakehome.presentationcommon.BaseViewModel
import io.reactivex.processors.PublishProcessor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class PathSelectorViewModel @Inject constructor(
    private val pathSelector: PathSelectorPage
) : BaseViewModel() {

    private val originStream = PublishProcessor.create<String>()
    private val destinationStream = PublishProcessor.create<String>()
    private val routeStream = PublishProcessor.create<Pair<Airport, Airport>>()

    private val _originLiveData by lazy {
        val liveData = MutableLiveData<ArrayList<Airport>>()
        liveData.value = ArrayList()
        liveData
    }
    private val _destinationLiveData by lazy {
        val liveData = MutableLiveData<ArrayList<Airport>>()
        liveData.value = ArrayList()
        liveData
    }
    private val _originAirportLiveData by lazy {
        MutableLiveData<Airport>()
    }
    private val _destinationAirportLiveData by lazy {
        MutableLiveData<Airport>()
    }
    private val _routesLiveData by lazy {
        MutableLiveData<List<Route>>()
    }
    private val _notFoundLiveData by lazy {
        MutableLiveData<Boolean>()
    }
    private val _clearOriginLiveData by lazy {
        MutableLiveData<Boolean>()
    }
    private val _clearDestinationLiveData by lazy {
        MutableLiveData<Boolean>()
    }
    private val _loadingLiveData by lazy {
        MutableLiveData<Boolean>()
    }

    val originLiveData: LiveData<out List<Airport>> = _originLiveData
    val destinationLiveData: LiveData<out List<Airport>> = _destinationLiveData
    val originAirportLiveData: LiveData<Airport> = _originAirportLiveData
    val destinationAirportLiveData: LiveData<Airport> = _destinationAirportLiveData
    val routesLiveData: LiveData<List<Route>> = _routesLiveData
    val notFoundLiveData: LiveData<Boolean> = _notFoundLiveData
    val cleanOriginLiveData: LiveData<Boolean> = _clearOriginLiveData
    val cleanDestinationLiveData: LiveData<Boolean> = _clearDestinationLiveData
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private val foundRoute = AtomicBoolean(false)

    init {
        originStream
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                _clearOriginLiveData.postValue(true)
                _originLiveData.postValue(ArrayList())
                pathSelector.checkAirport(it)
            }
            .subscribe({ result ->
                _originLiveData.postValue(_originLiveData.value?.apply { add(result) })
            }, { t: Throwable? -> Log.w("PathSelectorViewModel", "Failed to get origin search results", t) }).track()

        destinationStream
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                _clearDestinationLiveData.postValue(true)
                _destinationLiveData.postValue(ArrayList())
                pathSelector.checkAirport(it)
            }
            .subscribe({ result ->
                _destinationLiveData.postValue(_destinationLiveData.value?.apply { add(result) })
            }, { t: Throwable? -> Log.w("PathSelectorViewModel", "Failed to get destination search results", t) })
            .track()

        routeStream
            .switchMap {
                foundRoute.set(false)
                _loadingLiveData.postValue(true)
                pathSelector.getShortestPath(it.first, it.second)
                    .doOnComplete {
                        _loadingLiveData.postValue(false)
                        if (!foundRoute.get()) _notFoundLiveData.postValue(true)
                    }
            }
            .subscribe({ result ->
                foundRoute.set(true)
                _loadingLiveData.postValue(false)
                _routesLiveData.postValue(result)
            }, { t: Throwable? -> Log.w("PathSelectorViewModel", "Failed to get routes search results", t) }).track()
    }

    fun originSuggestions(text: String) {
        originStream.onNext(text)
    }

    fun destinationsSuggestions(text: String) {
        destinationStream.onNext(text)
    }

    fun findShortestRoute(originAirport: Airport, destinationAirport: Airport) {
        routeStream.onNext(Pair(originAirport, destinationAirport))
    }

    fun keepOriginAirport(airport: Airport?) {
        _originAirportLiveData.value = airport
    }

    fun keepDestinationAirport(airport: Airport?) {
        _destinationAirportLiveData.value = airport
    }

}