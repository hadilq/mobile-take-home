package com.github.hadilq.mobiletakehome

import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.github.hadilq.mobiletakehome.di.DataModule
import com.github.hadilq.mobiletakehome.di.MapActivityModule
import com.github.hadilq.mobiletakehome.di.PathSelectorActivityModule
import com.github.hadilq.mobiletakehome.di.app.AppComponent
import com.github.hadilq.mobiletakehome.di.app.AppModule
import com.github.hadilq.mobiletakehome.di.app.DatabaseModule
import com.github.hadilq.mobiletakehome.di.viewmodel.ViewModelModule
import com.github.hadilq.mobiletakehome.domain.entity.Airport
import com.github.hadilq.mobiletakehome.domain.repository.AirportRepository
import com.github.hadilq.mobiletakehome.domain.repository.DatabaseReadyRepository
import com.github.hadilq.mobiletakehome.domain.repository.RouteRepository
import com.github.hadilq.mobiletakehome.domain.usecase.MapPage
import com.github.hadilq.mobiletakehome.domain.usecase.PathSelectorPage
import com.github.hadilq.mobiletakehome.presentationpathselector.PathSelectorActivity
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import javax.inject.Inject
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
class PathSelectorActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<PathSelectorActivity> = ActivityTestRule(
        PathSelectorActivity::class.java,
        true, // initialTouchMode
        false
    )

    private lateinit var app: App

    @Inject
    lateinit var pathSelectorPage: PathSelectorPage

    @Before
    fun setup() {
        app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App

        val appComponent = DaggerPathSelectorActivityTest_TestAppComponent
            .builder()
            .domainModule(TestDomainModule())
            .application(app)
            .build()
        appComponent.inject(app)
        appComponent.inject(this)
    }

    @Test
    fun launchActivity() {
        activityRule.launchActivity(Intent())

        onView(withId(R.id.titleView)).check(matches(withText("Choose origin and destination airports")))
    }

    @Test
    fun suggestions() {
        val name = "AAAA"
        `when`(pathSelectorPage.checkAirport(any())).doReturn(Flowable.just(Airport(name, "A", "A", "AAA", 0.0, 0.0)))

        activityRule.launchActivity(Intent())

        onView(withId(R.id.originView)).perform(click()).perform(typeText("AAA"))
        onView(withText(name))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())
    }

    @Singleton
    @Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            DatabaseModule::class,
            ViewModelModule::class,
            DataModule::class,
            TestDomainModule::class,
            MapActivityModule::class,
            PathSelectorActivityModule::class
        ]
    )
    interface TestAppComponent : AppComponent {
        @Component.Builder
        interface Builder {
            @BindsInstance
            fun domainModule(module: TestDomainModule): Builder

            @BindsInstance
            fun application(app: App): Builder

            fun build(): TestAppComponent
        }

        fun inject(testClass: PathSelectorActivityTest)
    }

    @Module
    class TestDomainModule {

        private val databaseReadyRepository: DatabaseReadyRepository = mock()
        private val airportRepository: AirportRepository = mock()
        private val routeRepository: RouteRepository = mock()
        private val mapPage: MapPage = mock()
        private val pathSelectorPage: PathSelectorPage = mock()

        @Provides
        fun provideDatabaseReadyRepository(): DatabaseReadyRepository = databaseReadyRepository

        @Provides
        fun provideAirportRepository(): AirportRepository = airportRepository

        @Provides
        fun provideRouteRepository(): RouteRepository = routeRepository

        @Provides
        fun provideMapPage(): MapPage = mapPage

        @Provides
        fun providePathSelectorPage(): PathSelectorPage = pathSelectorPage

    }
}