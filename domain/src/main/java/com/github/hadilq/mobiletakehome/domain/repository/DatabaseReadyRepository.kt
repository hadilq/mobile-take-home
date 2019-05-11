package com.github.hadilq.mobiletakehome.domain.repository

import io.reactivex.Flowable

interface DatabaseReadyRepository {

    /**
     * Returns an stream to notify when database is ready. True is ready.
     */
    fun isDatabaseReady(): Flowable<Boolean>
}