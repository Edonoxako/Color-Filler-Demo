package com.edonoxako.colorfillerdemo.domain.algorithm

import android.graphics.Point
import com.edonoxako.colorfillerdemo.common.neighbourPoints
import io.reactivex.Flowable
import io.reactivex.functions.BiConsumer
import java.util.*
import java.util.concurrent.Callable

class DfsFillAlgorithm(
    private val points: MutableMap<Point, Boolean>,
    private val startingPoint: Point
) : FillAlgorithm {

    private val stack = LinkedList<Point>().apply { push(startingPoint) }
    private val fillValue = !points.getValue(startingPoint)

    override fun run(): Flowable<Map<Point, Boolean>> {
        return Flowable.generate(
            Callable { stack },
            BiConsumer { _, emitter ->
                if (stack.isNotEmpty()) {
                    val point = stack.pop()
                    point.neighbourPoints.forEach(::tryToPush)
                    points[point] = fillValue
                    emitter.onNext(points.toMap())
                } else {
                    emitter.onComplete()
                }
            }
        )
    }

    private fun tryToPush(point: Point) {
        if (stack.contains(point)) return
        val pointValue = points[point] ?: return
        if (pointValue != fillValue) stack.push(point)
    }
}