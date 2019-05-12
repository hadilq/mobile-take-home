package com.github.hadilq.mobiletakehome.presentationmap

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModelProvider
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.presentationcommon.*
import kotlinx.android.synthetic.main.map_activity.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import javax.inject.Inject


class MapActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var intentFactory: IntentFactory

    private lateinit var viewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        setContentView(R.layout.map_activity)

        setupMap()

        viewModel = viewModel(viewModelFactory) {
            airportsLiveData.observe(::airports)
            loadingLiveData.observe(::loading)
            checkDatabase()
        }

        addRouteView.setOnClickListener {
            viewModel.loadingLiveData.value?.let {
                if (!it) {
                    val intent = intentFactory.create(IntentFactory.Page.PATH_SELECTOR)
                    startActivityForResult(intent, PAGE_SELECTOR_REQUEST_CODE)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PAGE_SELECTOR_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.apply {
                        getStringArrayExtra(IntentFactory.ResultKey.PAGE_SELECTED_AIRPORTS.key)?.apply {
                            viewModel.loadAirports(this)
                        }
                    }
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setupMap() {
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        mapView.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
        mapView.controller.setZoom(3.0)
    }

    private fun airports(airports: List<Airport>) {

    }

    private fun loading(loading: Boolean) {
        if (loading) {
            progressBar.visible()
            addRouteView.invisible()
        } else {
            progressBar.gone()
            addRouteView.visible()
        }
    }

    companion object {
        private const val PAGE_SELECTOR_REQUEST_CODE = 300
    }
}