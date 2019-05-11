package com.github.hadilq.mobiletakehome

import android.content.Context
import android.content.Intent
import com.github.hadilq.mobiletakehome.presentationcommon.IntentFactory
import com.github.hadilq.mobiletakehome.presentationcommon.IntentFactory.Page
import com.github.hadilq.mobiletakehome.presentationmap.MapActivity
import com.github.hadilq.mobiletakehome.presentationpathselector.PathSelectorActivity
import javax.inject.Inject

class IntentFactoryImpl @Inject constructor(
    private val context: Context
) : IntentFactory {

    override fun create(page: Page): Intent =
        when (page) {
            Page.MAP -> Intent(context, MapActivity::class.java)
            Page.PATH_SELECTOR -> Intent(context, PathSelectorActivity::class.java)
        }
}