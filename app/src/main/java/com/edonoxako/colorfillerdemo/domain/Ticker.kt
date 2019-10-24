package com.edonoxako.colorfillerdemo.domain

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class Ticker {

    companion object {
        private const val ONE_SECOND_IN_MILLIS = 1000L
    }

    val ticks: Flowable<Long>
        get() = ticksSubject
            .toFlowable(BackpressureStrategy.BUFFER)
            .switchMap { speed -> Flowable.interval(speed, TimeUnit.MILLISECONDS) }


    private val ticksSubject = BehaviorSubject.create<Long>()

    fun updateTicksSpeed(speed: Int) {
        ticksSubject.onNext(calculateRealSpeed(speed))
    }

    private fun calculateRealSpeed(speed: Int) = if (speed == 0) {
        ONE_SECOND_IN_MILLIS
    } else {
        ONE_SECOND_IN_MILLIS / speed
    }
}