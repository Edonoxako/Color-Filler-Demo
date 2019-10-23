package com.edonoxako.colorfillerdemo.domain

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class Ticker {

    companion object {
        private const val MAX_SPEED = 500L
        private const val MIN_SPEED = 10L
    }

    val ticks: Flowable<Long>
        get() = ticksSubject
            .toFlowable(BackpressureStrategy.BUFFER)
            .switchMap { speed -> Flowable.interval(speed, TimeUnit.MILLISECONDS) }


    private val ticksSubject = BehaviorSubject.create<Long>()

    fun updateTicksSpeed(speed: Int) {
        ticksSubject.onNext(calculateRealSpeed(speed))
    }

    private fun calculateRealSpeed(speed: Int) =
        MIN_SPEED + (MAX_SPEED - (speed * 0.01 * MAX_SPEED)).toLong()
}