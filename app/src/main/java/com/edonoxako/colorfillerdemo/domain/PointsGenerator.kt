package com.edonoxako.colorfillerdemo.domain

import android.graphics.Point
import com.edonoxako.colorfillerdemo.domain.model.Size
import io.reactivex.Single
import kotlin.random.Random

class PointsGenerator {

    fun generate(size: Size): Single<Map<Point, Boolean>> {
        return Single.fromCallable {
            val random = Random(System.currentTimeMillis())
            val result = mutableMapOf<Point, Boolean>()

            for (x in 0 until size.width) {
                for (y in 0 until size.height) {
                    result[Point(x, y)] = random.nextBoolean()
                }
            }

            result
        }
    }
}