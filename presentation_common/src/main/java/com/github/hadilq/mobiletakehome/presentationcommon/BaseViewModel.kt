package com.github.hadilq.mobiletakehome.presentationcommon

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    protected fun Disposable.track() = disposables.add(this)

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}