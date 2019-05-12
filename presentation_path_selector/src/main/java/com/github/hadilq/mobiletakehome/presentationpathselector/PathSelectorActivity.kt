package com.github.hadilq.mobiletakehome.presentationpathselector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
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

    private var originAirport: Airport? = null
    private var destinationAirport: Airport? = null

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
        }
    }

    private fun originSuggestion(airports: List<Airport>) = originAdapter.addAirports(airports)

    private fun destinationSuggestion(airports: List<Airport>) = destinationAdapter.addAirports(airports)

    private fun foundedRoute(routes: List<Route>) {
        resultView.visible()
        showView.visible()
        progressBar.gone()
        errorView.gone()
        sendResult(routes)
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

        showView.setOnClickListener { finish() }
    }

    private fun setupAutoCompletes() {
        originView.threshold = 1
        originView.setAdapter(originAdapter)
        originView.setDropDownBackgroundResource(android.R.color.transparent)
        originView.setOnItemClickListener { _, _, position, _ ->
            originAirport = originAdapter.getItem(position)
            originAdapter.clear()
            checkForRoute()
        }
        originView.doOnTextChanged { text, _, _, _ ->
            viewModel.originSuggestions(text.toString().trim())
            disableResultViews()
        }
        originView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                checkForRoute()
                true
            } else {
                false
            }
        }

        destinationView.threshold = 1
        destinationView.setAdapter(destinationAdapter)
        destinationView.setDropDownBackgroundResource(android.R.color.transparent)
        destinationView.setOnItemClickListener { _, _, position, _ ->
            destinationAirport = destinationAdapter.getItem(position)
            destinationAdapter.clear()
            checkForRoute()
        }
        destinationView.doOnTextChanged { text, _, _, _ ->
            viewModel.destinationsSuggestions(text.toString().trim())
            disableResultViews()
        }
        destinationView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                checkForRoute()
                true
            } else {
                false
            }
        }
    }

    private fun checkForRoute() {
        originAirport?.let { origin ->
            destinationAirport?.let { destination ->
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