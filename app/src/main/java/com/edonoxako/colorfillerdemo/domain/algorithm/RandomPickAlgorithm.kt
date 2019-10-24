package com.edonoxako.colorfillerdemo.domain.algorithm

import android.graphics.Point
import com.edonoxako.colorfillerdemo.common.neighbourPoints
import io.reactivex.Flowable
import io.reactivex.functions.BiConsumer
import java.util.*
import java.util.concurrent.Callable

class RandomPickAlgorithm(
    private val points: MutableMap<Point, Boolean>,
    private val startingPoint: Point
) : FillAlgorithm {

    private val list = LinkedList<Point>().apply { add(startingPoint) }
    private val fillValue = !points.getValue(startingPoint)
    private val random = Random(System.currentTimeMillis())

    override fun run(): Flowable<Pair<Point, Boolean>> {
        return Flowable.generate(
            Callable { list },
            BiConsumer { _, emitter ->
                if (list.isNotEmpty()) {
                    val point = list.removeAt(random.nextInt(list.size))
                    point.neighbourPoints.forEach(::tryToPush)
                    points[point] = fillValue
                    emitter.onNext(point to fillValue)
                } else {
                    emitter.onComplete()
                }
            }
        )
    }

    private fun tryToPush(point: Point) {
        if (list.contains(point)) return
        val pointValue = points[point] ?: return
        if (pointValue != fillValue) list.push(point)
    }
}