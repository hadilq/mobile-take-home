package com.github.hadilq.mobiletakehome.di

import com.github.hadilq.mobiletakehome.presentationmap.MapActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MapActivityModule {

    @ContributesAndroidInjector(modules = [])
    internal abstract fun get(): MapActivity
}