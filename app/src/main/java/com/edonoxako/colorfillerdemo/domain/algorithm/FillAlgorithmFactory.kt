package com.edonoxako.colorfillerdemo.domain.algorithm

import android.graphics.Point
import com.edonoxako.colorfillerdemo.domain.model.AlgorithmName

class FillAlgorithmFactory {

    fun getAlgorithm(
        algorithmName: AlgorithmName,
        points: Map<Point, Boolean>,
        startingPoint: Point
    ): FillAlgorithm {
        return when (algorithmName) {
            AlgorithmName.BFS -> BfsFillAlgorithm(points.toMutableMap(), startingPoint)
            AlgorithmName.DFS -> DfsFillAlgorithm(points.toMutableMap(), startingPoint)
            AlgorithmName.RANDOM -> RandomPickAlgorithm(points.toMutableMap(), startingPoint)
        }
    }
}