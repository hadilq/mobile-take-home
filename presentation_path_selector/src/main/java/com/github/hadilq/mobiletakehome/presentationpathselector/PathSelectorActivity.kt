package com.github.hadilq.mobiletakehome.presentationpathselector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
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

    private lateinit var viewModel: PathSelectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.path_selector_activity)

        setupBottomSheet()
        setupAutoCompletes()

        viewModel = viewModel(viewModelFactory) {
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
    }

    private fun setupAutoCompletes() {
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

    fun sendResult(routes: List<Route>) {
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