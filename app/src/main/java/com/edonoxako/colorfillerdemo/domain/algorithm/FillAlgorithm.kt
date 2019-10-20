package com.edonoxako.colorfillerdemo.domain.algorithm

import android.graphics.Point
import io.reactivex.Flowable

interface FillAlgorithm {

    fun run(): Flowable<Point>
}