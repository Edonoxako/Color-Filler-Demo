package com.edonoxako.colorfillerdemo.domain

import android.graphics.Point
import com.edonoxako.colorfillerdemo.domain.algorithm.FillAlgorithm
import com.edonoxako.colorfillerdemo.domain.algorithm.FillAlgorithmFactory
import com.edonoxako.colorfillerdemo.domain.model.AlgorithmName
import com.edonoxako.colorfillerdemo.domain.model.Size
import io.reactivex.Flowable
import io.reactivex.Single

class ColorFillerInteractor(
    private val fillAlgorithmFactory: FillAlgorithmFactory,
    private val pointsRepository: PointsRepository,
    private val pointsGenerator: PouintsGenerator
) {

    fun generatePoints(size: Size): Single<Map<Point, Boolean>> {
        return pointsGenerator.generate(size)
            .flatMapCompletable(pointsRepository::updatePoints)
            .andThen(pointsRepository.getPoints())
    }

    fun run(algorithmName: AlgorithmName, startingPoint: Point): Flowable<Point> {
        return pointsRepository.getPoints()
            .map { points -> getAlgorithm(algorithmName, points, startingPoint) }
            .flatMapPublisher { it.run() }
    }

    private fun getAlgorithm(
        algorithmName: AlgorithmName,
        points: Map<Point, Boolean>,
        startingPoint: Point
    ): FillAlgorithm {
        return fillAlgorithmFactory.getAlgorithm(algorithmName, points, startingPoint)
    }
}