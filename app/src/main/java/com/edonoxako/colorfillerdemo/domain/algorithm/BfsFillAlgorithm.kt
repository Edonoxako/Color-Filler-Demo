package com.edonoxako.colorfillerdemo.domain.algorithm

import android.graphics.Point
import com.edonoxako.colorfillerdemo.common.neighbourPoints
import com.edonoxako.colorfillerdemo.common.top
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.disposables.Disposables
import io.reactivex.functions.BiConsumer
import timber.log.Timber
import java.util.*
import java.util.concurrent.Callable

class BfsFillAlgorithm(
    private val points: MutableMap<Point, Boolean>,
    private val startingPoint: Point
) : FillAlgorithm {

    private val queue = LinkedList<Point>().apply { push(startingPoint) }
    private val fillValue = !points.getValue(startingPoint)

    override fun run(): Flowable<Map<Point, Boolean>> {
        return Flowable.generate(
            Callable { queue },
            BiConsumer { state, emitter ->
                if (state.isNotEmpty()) {
                    val point = queue.removeLast()
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
        if (queue.contains(point)) return
        val pointValue = points[point] ?: return
        if (pointValue != fillValue) queue.push(point)
    }
}