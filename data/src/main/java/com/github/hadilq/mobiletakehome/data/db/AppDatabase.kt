package com.github.hadilq.mobiletakehome.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.hadilq.mobiletakehome.data.db.dao.AirlineDao
import com.github.hadilq.mobiletakehome.data.db.dao.AirportDao
import com.github.hadilq.mobiletakehome.data.db.dao.RouteDao
import com.github.hadilq.mobiletakehome.data.db.table.AirlineRow
import com.github.hadilq.mobiletakehome.data.db.table.AirportRow
import com.github.hadilq.mobiletakehome.data.db.table.RouteRow
import com.github.hadilq.mobiletakehome.data.utils.CsvReader
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import java.lang.ref.WeakReference
import java.util.concurrent.Executor


@Database(entities = [AirlineRow::class, AirportRow::class, RouteRow::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    private val databaseCreated by lazy {
        val processor = BehaviorProcessor.create<Boolean>()
        processor.onNext(false)
        processor
    }

    abstract fun airlineDao(): AirlineDao
    abstract fun airportDao(): AirportDao
    abstract fun routeDao(): RouteDao

    private fun setDatabaseCreated() {
        databaseCreated.onNext(true)
    }

    fun isDatabaseCreated(): Flowable<Boolean> = databaseCreated.hide()

    private fun insertRoutes() {
        airlineDao().save(*csvReader.loadAirlines().values.toTypedArray())
        airportDao().save(*csvReader.loadAirports().values.toTypedArray())
        routeDao().save(*csvReader.loadRouts().toTypedArray())
    }

    companion object {
        private const val DATABASE_NAME = "mobile-take-home-database"

        private var contextRef: WeakReference<Context>? = null
        lateinit var csvReader: CsvReader
        lateinit var executor: Executor

        fun setContext(context: Context) {
            contextRef = WeakReference(context)
        }

        val sInstance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            contextRef?.let {
                it.get()?.let { context ->
                    buildDatabase(context, executor)
                }
            } ?: let {
                throw IllegalStateException("Context must be available")
            }
        }

        /**
         * Build the database. [RoomDatabase.Builder.build] only sets up the database configuration and
         * creates a new instance of the database.
         * The SQLite database is only created when it's accessed for the first time.
         *
         * @see: https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/db/AppDatabase.java
         */
        private fun buildDatabase(
            appContext: Context,
            executor: Executor
        ): AppDatabase {
            val builder = Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
            return if (appContext.getDatabasePath(DATABASE_NAME).exists()) {
                executor.execute { sInstance.setDatabaseCreated() }
                builder.build()
            } else {
                builder.addCallback(object : RoomDatabase.Callback() {

                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        executor.execute {
                            sInstance.insertRoutes()

                            // notify that the database was created and it's ready to be used
                            sInstance.setDatabaseCreated()
                        }
                    }
                }).build()
            }
        }
    }
}