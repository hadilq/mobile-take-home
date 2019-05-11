package com.github.hadilq.mobiletakehome.di

import com.github.hadilq.mobiletakehome.presentationpathselector.PathSelectorActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PathSelectorActivityModule {

    @ContributesAndroidInjector(modules = [])
    internal abstract fun get(): PathSelectorActivity
}