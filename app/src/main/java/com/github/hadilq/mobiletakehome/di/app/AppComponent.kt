/***
 * Copyright 2019 Hadi Lashkari Ghouchani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * */
package com.github.hadilq.mobiletakehome.di.app

import com.github.hadilq.mobiletakehome.App
import com.github.hadilq.mobiletakehome.di.DataModule
import com.github.hadilq.mobiletakehome.di.DomainModule
import com.github.hadilq.mobiletakehome.di.MapActivityModule
import com.github.hadilq.mobiletakehome.di.PathSelectorActivityModule
import com.github.hadilq.mobiletakehome.di.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DatabaseModule::class,
        ViewModelModule::class,
        DataModule::class,
        DomainModule::class,
        MapActivityModule::class,
        PathSelectorActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}