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