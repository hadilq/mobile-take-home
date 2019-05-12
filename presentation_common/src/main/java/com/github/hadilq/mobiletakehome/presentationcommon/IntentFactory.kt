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
package com.github.hadilq.mobiletakehome.presentationcommon

import android.content.Intent

interface IntentFactory {

    fun create(page: Page): Intent

    enum class Page(
        val id: Int
    ) {
        MAP(0), PATH_SELECTOR(1)
    }

    enum class ResultKey(
        val key: String
    ) {
        PAGE_SELECTED_AIRPORTS("PAGE_SELECTED_AIRPORTS")
    }
}