package com.edonoxako.colorfillerdemo.domain

import android.graphics.Point
import com.edonoxako.colorfillerdemo.domain.algorithm.FillAlgorithm
import com.edonoxako.colorfillerdemo.domain.algorithm.FillAlgorithmFactory
import com.edonoxako.colorfillerdemo.domain.model.AlgorithmName
import com.edonoxako.colorfillerdemo.domain.model.Size
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ColorFillerInteractor(
    private val fillAlgorithmFactory: FillAlgorithmFactory,
    private val pointsRepository: PointsRepository,
    private val pointsGenerator: PointsGenerator,
    private val ticker: Ticker
) {

    fun generatePoints(size: Size): Single<Map<Point, Boolean>> {
        return pointsGenerator.generate(size)
            .flatMapCompletable(pointsRepository::updatePoints)
            .andThen(pointsRepository.getPoints())
    }

    fun run(algorithmName: AlgorithmName, startingPoint: Point): Flowable<Map<Point, Boolean>> {
        return pointsRepository.getPoints()
            .map { points -> getAlgorithm(algorithmName, points, startingPoint) }
            .flatMapPublisher { it.run() }
            .zipWith(ticker.ticks) { point, _ -> point }
            .doOnNext(pointsRepository::updatePointsSync)
    }

    private fun getAlgorithm(
        algorithmName: AlgorithmName,
        points: Map<Point, Boolean>,
        startingPoint: Point
    ): FillAlgorithm {
        return fillAlgorithmFactory.getAlgorithm(algorithmName, points, startingPoint)
    }

    fun updateSpeed(speed: Int) {
        Timber.d("$speed")
        ticker.updateTicksSpeed(speed)
    }
}