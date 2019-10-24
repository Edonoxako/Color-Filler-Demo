package com.edonoxako.colorfillerdemo.common

import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import timber.log.Timber

fun ViewGroup.inflateSelf(@LayoutRes layoutRes: Int) {
    View.inflate(context, layoutRes, this)
}

fun View.showSoftKeyboard() {
    post {
        if (this.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}

fun <T> Flowable<T>.subscribeLoggingError(handler: (T) -> Unit): Disposable {
    return subscribe(handler::invoke, Timber::e)
}

fun <T> Single<T>.subscribeLoggingError(handler: (T) -> Unit): Disposable {
    return subscribe(handler::invoke, Timber::e)
}

fun Completable.subscribeLoggingError(handler: () -> Unit): Disposable {
    return subscribe(handler::invoke, Timber::e)
}

val Point.right get() = Point(x.inc(), y)
val Point.left get() = Point(x.dec(), y)
val Point.top get() = Point(x, y.dec())
val Point.bot get() = Point(x, y.inc())

val Point.neighbourPoints get() = listOf(
    top, right, bot, left
)