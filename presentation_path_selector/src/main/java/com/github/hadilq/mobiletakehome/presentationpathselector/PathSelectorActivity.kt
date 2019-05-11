package com.github.hadilq.mobiletakehome.presentationpathselector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.github.hadilq.mobiletakehome.domain.entity.Route
import com.github.hadilq.mobiletakehome.presentationcommon.BaseActivity
import com.github.hadilq.mobiletakehome.presentationcommon.IntentFactory

class PathSelectorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.path_selector_activity)
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