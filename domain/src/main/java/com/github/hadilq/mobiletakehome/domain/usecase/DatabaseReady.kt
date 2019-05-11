package com.github.hadilq.mobiletakehome.domain.usecase

import io.reactivex.Flowable

interface DatabaseReady {

    fun isDatabaseReady(): Flowable<Boolean>
}