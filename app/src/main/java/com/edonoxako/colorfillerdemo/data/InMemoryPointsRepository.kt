package com.edonoxako.colorfillerdemo.data

import android.graphics.Point
import com.edonoxako.colorfillerdemo.domain.PointsRepository
import io.reactivex.Completable
import io.reactivex.Single

class InMemoryPointsRepository : PointsRepository {

    private var points = mapOf<Point, Boolean>()

    private val pointsKeyCache = mutableMapOf<String, Map<Point, Boolean>>()

    override fun updatePoints(points: Map<Point, Boolean>): Completable {
        return Completable.fromAction {
            pointsKeyCache.clear()
            this.points = points
        }
    }

    override fun updatePointsByKey(key: String, points: Map<Point, Boolean>) {
        pointsKeyCache[key] = points
    }

    override fun getPoints(): Single<Map<Point, Boolean>> {
        return Single.fromCallable { points }
    }

    override fun getPointsByKey(key: String): Single<Map<Point, Boolean>> {
        return Single.fromCallable {
            if (!pointsKeyCache.contains(key)) {
                pointsKeyCache[key] = points.toMap()
            }
            return@fromCallable pointsKeyCache[key]
        }
    }
}