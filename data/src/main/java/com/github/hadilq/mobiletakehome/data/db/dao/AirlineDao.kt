package com.github.hadilq.mobiletakehome.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.hadilq.mobiletakehome.data.db.table.AirlineRow
import io.reactivex.Flowable

@Dao
interface AirlineDao {

    @Query("SELECT * FROM airline")
    fun all(): Flowable<AirlineRow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg airline: AirlineRow)
}