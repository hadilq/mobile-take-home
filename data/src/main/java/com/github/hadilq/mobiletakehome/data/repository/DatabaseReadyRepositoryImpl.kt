package com.github.hadilq.mobiletakehome.data.repository

import com.github.hadilq.mobiletakehome.data.db.AppDatabase
import com.github.hadilq.mobiletakehome.domain.repository.DatabaseReadyRepository
import io.reactivex.Flowable

class DatabaseReadyRepositoryImpl(
    private val database: AppDatabase
) : DatabaseReadyRepository {

    override fun isDatabaseReady(): Flowable<Boolean> =
        database.isDatabaseCreated().doOnSubscribe {
            // Trigger Database creation
            database.airlineDao().all().subscribe()
        }
}