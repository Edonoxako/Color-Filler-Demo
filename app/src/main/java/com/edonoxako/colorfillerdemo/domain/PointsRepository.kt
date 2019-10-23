package com.edonoxako.colorfillerdemo.domain

import android.graphics.Point
import io.reactivex.Completable
import io.reactivex.Single

interface PointsRepository {

    fun getPoints(): Single<Map<Point, Boolean>>

    fun updatePoints(points: Map<Point, Boolean>): Completable

    fun updatePointsSync(points: Map<Point, Boolean>)
}