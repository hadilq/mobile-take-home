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
package com.github.hadilq.mobiletakehome.presentationpathselector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.entity.Route
import com.github.hadilq.mobiletakehome.presentationcommon.BaseActivity
import com.github.hadilq.mobiletakehome.presentationcommon.IntentFactory
import com.github.hadilq.mobiletakehome.presentationcommon.gone
import com.github.hadilq.mobiletakehome.presentationcommon.visible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.path_selector_activity.*
import javax.inject.Inject

class PathSelectorActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var originAdapter: AirportAutoCompleteAdapter
    @Inject
    lateinit var destinationAdapter: AirportAutoCompleteAdapter

    private lateinit var viewModel: PathSelectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.path_selector_activity)

        setupBottomSheet()
        setupAutoCompletes()

        viewModel = viewModel(viewModelFactory) {
            originLiveData.observe(::originSuggestion)
            destinationLiveData.observe(::destinationSuggestion)
            routesLiveData.observe(::foundedRoute)
            notFoundLiveData.observe(::notFoundRoute)
            cleanOriginLiveData.observe(::clearOriginAdapter)
            cleanDestinationLiveData.observe(::clearDestinationAdapter)
            loadingLiveData.observe(::loading)
            originAirportLiveData.observe { checkForRoute() }
            destinationAirportLiveData.observe { checkForRoute() }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.stay, R.anim.slide_down)
    }

    private fun originSuggestion(airports: List<Airport>) = originAdapter.addAirports(airports)

    private fun destinationSuggestion(airports: List<Airport>) = destinationAdapter.addAirports(airports)

    private fun foundedRoute(@Suppress("UNUSED_PARAMETER") routes: List<Route>) {
        resultView.visible()
        showView.visible()
        progressBar.gone()
        errorView.gone()
    }

    private fun notFoundRoute(@Suppress("UNUSED_PARAMETER") notFound: Boolean) {
        showView.gone()
        resultView.gone()
        progressBar.gone()
        errorView.visible()
    }

    private fun clearOriginAdapter(@Suppress("UNUSED_PARAMETER") clear: Boolean) {
        originAdapter.clear()
    }

    private fun clearDestinationAdapter(@Suppress("UNUSED_PARAMETER") clear: Boolean) {
        destinationAdapter.clear()
    }

    private fun loading(loading: Boolean) {
        if (loading) {
            progressBar.visible()
        } else {
            progressBar.gone()
        }
    }

    private fun setupBottomSheet() {
        pathSelectorLayout.setOnClickListener { finish() }
        bottomSheetLayout.setOnClickListener { }
        val behaviour = BottomSheetBehavior.from(bottomSheetLayout)
        behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        behaviour.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            private var lastOffset = bottomSheetLayout.measuredHeight.toFloat()

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (lastOffset >= slideOffset) {
                    moving(true)
                } else {
                    moving(false)
                }
                lastOffset = slideOffset
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> finish()
                    else -> {
                    }
                }
            }

        })

        showView.setOnClickListener {
            viewModel.routesLiveData.value?.let {
                sendResult(it)
                finish()
            }
        }
    }

    private fun setupAutoCompletes() {
        originView.threshold = 1
        originView.setAdapter(originAdapter)
        originView.setDropDownBackgroundResource(android.R.color.transparent)
        originView.setOnItemClickListener { _, _, position, _ ->
            viewModel.keepOriginAirport(originAdapter.getItem(position))
            originAdapter.clear()
        }
        originView.doOnTextChanged { text, _, _, _ ->
            viewModel.originSuggestions(text.toString().trim())
            disableResultViews()
        }

        destinationView.threshold = 1
        destinationView.setAdapter(destinationAdapter)
        destinationView.setDropDownBackgroundResource(android.R.color.transparent)
        destinationView.setOnItemClickListener { _, _, position, _ ->
            viewModel.keepDestinationAirport(destinationAdapter.getItem(position))
            destinationAdapter.clear()
        }
        destinationView.doOnTextChanged { text, _, _, _ ->
            viewModel.destinationsSuggestions(text.toString().trim())
            disableResultViews()
        }
    }

    private fun checkForRoute() {
        viewModel.originAirportLiveData.value?.let { origin ->
            viewModel.destinationAirportLiveData.value?.let { destination ->
                viewModel.findShortestRoute(origin, destination)
            } ?: run { disableResultViews() }
        } ?: run { disableResultViews() }
    }

    private fun disableResultViews() {
        showView.gone()
        resultView.gone()
        errorView.gone()
        progressBar.gone()
    }

    private fun moving(up: Boolean) {
        if (up) {
            doubleUpView.visible()
            doubleDownView.gone()
        } else {
            doubleUpView.gone()
            doubleDownView.visible()
        }
    }

    private fun sendResult(routes: List<Route>) {
        if (routes.isNotEmpty()) {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(
                    IntentFactory.ResultKey.PAGE_SELECTED_AIRPORTS.key,
                    ArrayList<String>().apply {
                        add(routes[0].origin.iata)
                        addAll(routes.map { it.destination.iata })
                    }.toTypedArray()
                )
            })
        } else {
            setResult(Activity.RESULT_CANCELED)
        }
    }
}