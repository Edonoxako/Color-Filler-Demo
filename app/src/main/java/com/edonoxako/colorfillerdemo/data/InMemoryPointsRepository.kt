package com.edonoxako.colorfillerdemo.data

import android.graphics.Point
import com.edonoxako.colorfillerdemo.domain.PointsRepository
import io.reactivex.Completable
import io.reactivex.Single

class InMemoryPointsRepository : PointsRepository {

    private var points = mapOf<Point, Boolean>()

    @Synchronized
    override fun updatePoints(points: Map<Point, Boolean>): Completable {
        return Completable.fromAction {
            this.points = points
        }
    }

    @Synchronized
    override fun updatePointsSync(points: Map<Point, Boolean>) {
        this.points = points
    }

    override fun getPoints(): Single<Map<Point, Boolean>> {
        return Single.fromCallable { points }
    }
}