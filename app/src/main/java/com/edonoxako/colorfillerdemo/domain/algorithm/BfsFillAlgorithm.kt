package com.edonoxako.colorfillerdemo.domain.algorithm

import android.graphics.Point
import com.edonoxako.colorfillerdemo.common.neighbourPoints
import com.edonoxako.colorfillerdemo.common.top
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.disposables.Disposables
import java.util.*

class BfsFillAlgorithm(
    private val points: MutableMap<Point, Boolean>,
    private val startingPoint: Point
) : FillAlgorithm {

    private val queue = LinkedList<Point>().apply { push(startingPoint) }
    private val fillValue = !points.getValue(startingPoint)

    override fun run(): Flowable<Point> {
        return Flowable.create(
            ::createSource,
            BackpressureStrategy.BUFFER
        )
    }

    private fun createSource(emitter: FlowableEmitter<Point>) {
        emitter.setDisposable(
            Disposables.fromAction { queue.clear() }
        )

        while (queue.isNotEmpty()) {
            val point = queue.removeLast()
            point.neighbourPoints.forEach(::tryToPush)
            points[point] = fillValue
            emitter.onNext(point)
        }

        emitter.onComplete()
    }

    private fun tryToPush(point: Point) {
        if (queue.contains(point)) return
        val pointValue = points[point] ?: return
        if (pointValue != fillValue) queue.push(point)
    }
}