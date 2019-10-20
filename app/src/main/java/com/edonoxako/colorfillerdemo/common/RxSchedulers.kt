package com.edonoxako.colorfillerdemo.common

import io.reactivex.Scheduler

interface RxSchedulers {

    val mainThread: Scheduler

    val io: Scheduler

    val computation: Scheduler
}