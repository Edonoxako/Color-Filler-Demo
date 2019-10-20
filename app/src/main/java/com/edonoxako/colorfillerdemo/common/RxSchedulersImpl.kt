package com.edonoxako.colorfillerdemo.common

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class RxSchedulersImpl : RxSchedulers {

    override val mainThread: Scheduler = AndroidSchedulers.mainThread()

    override val io: Scheduler = Schedulers.io()

    override val computation: Scheduler = Schedulers.computation()
}