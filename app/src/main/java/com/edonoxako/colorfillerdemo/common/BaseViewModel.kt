package com.edonoxako.colorfillerdemo.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun dispose() = compositeDisposable.clear()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    protected fun safeSubscribe(disposableSource: () -> Disposable) {
        compositeDisposable.add(disposableSource.invoke())
    }
}