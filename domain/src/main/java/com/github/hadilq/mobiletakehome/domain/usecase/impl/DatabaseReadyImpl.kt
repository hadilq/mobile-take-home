package com.github.hadilq.mobiletakehome.domain.usecase.impl

import com.github.hadilq.mobiletakehome.domain.repository.DatabaseReadyRepository
import com.github.hadilq.mobiletakehome.domain.usecase.DatabaseReady
import io.reactivex.Flowable

class DatabaseReadyImpl(
    private val repository: DatabaseReadyRepository
) : DatabaseReady {

    override fun isDatabaseReady(): Flowable<Boolean> = repository.isDatabaseReady()
}