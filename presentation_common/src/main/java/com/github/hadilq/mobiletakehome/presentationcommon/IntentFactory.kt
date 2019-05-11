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