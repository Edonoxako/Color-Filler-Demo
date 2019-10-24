package com.edonoxako.colorfillerdemo.presentation.viewmodel

import android.graphics.Point
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.edonoxako.colorfillerdemo.common.BaseViewModel
import com.edonoxako.colorfillerdemo.common.RxSchedulers
import com.edonoxako.colorfillerdemo.domain.ColorFillerInteractor
import com.edonoxako.colorfillerdemo.domain.model.AlgorithmName
import com.edonoxako.colorfillerdemo.domain.model.Size
import com.edonoxako.colorfillerdemo.common.subscribeLoggingError
import timber.log.Timber

class MainViewModel(
    private val colorFillerInteractor: ColorFillerInteractor,
    private val rxSchedulers: RxSchedulers
) : BaseViewModel() {

    companion object {
        private val DEFAULT_SIZE = Size(50, 50)
    }

    val firstGeneratedPoints: LiveData<MutableMap<Point, Boolean>>
        get() = _firstGeneratedPoints

    val secondGeneratedPoints: LiveData<MutableMap<Point, Boolean>>
        get() = _secondGeneratedPoints

    val firstAlgorithmOutput: MutableLiveData<Long>
        get() = _firstAlgorithmTick

    val secondAlgorithmOutput: MutableLiveData<Long>
        get() = _secondAlgorithmTick

    private val _firstGeneratedPoints = MutableLiveData<MutableMap<Point, Boolean>>()
    private val _secondGeneratedPoints = MutableLiveData<MutableMap<Point, Boolean>>()
    private val _firstAlgorithmTick = MutableLiveData<Long>()
    private val _secondAlgorithmTick = MutableLiveData<Long>()

    var imageSize = DEFAULT_SIZE
        set(value) {
            dispose()
            field = value
        }

    var firstAlgorithmName = AlgorithmName.BFS
        set(value) {
            if (field != value) dispose()
            field = value
        }

    var secondAlgorithmName = AlgorithmName.DFS
        set(value) {
            if (field != value) dispose()
            field = value
        }

    init {
        generatePoints()
    }

    fun generatePoints() {
        dispose()
        safeSubscribe {
            colorFillerInteractor.generatePoints(imageSize)
                .subscribeOn(rxSchedulers.computation)
                .observeOn(rxSchedulers.mainThread)
                .subscribeLoggingError { points ->
                    _firstGeneratedPoints.value = points.toMutableMap()
                    _secondGeneratedPoints.value = points.toMutableMap()
                }
        }
    }

    fun updateAlgorithmSpeed(percent: Int) {
        colorFillerInteractor.updateSpeed(percent)
    }

    fun start(startingPoint: Point) {
        Timber.d("Start $startingPoint")
        dispose()
        runAlgorithm(_firstGeneratedPoints.value!!, _firstAlgorithmTick, firstAlgorithmName, startingPoint)
        runAlgorithm(_secondGeneratedPoints.value!!, _secondAlgorithmTick, secondAlgorithmName, startingPoint)
    }

    private fun runAlgorithm(
        points: MutableMap<Point, Boolean>,
        tickLiveData: MutableLiveData<Long>,
        algorithmName: AlgorithmName,
        startingPoint: Point
    ) {
        safeSubscribe {
            colorFillerInteractor.run(points, algorithmName, startingPoint)
                .subscribeOn(rxSchedulers.computation)
                .observeOn(rxSchedulers.mainThread)
                .subscribeLoggingError { pointAndFillValue ->
                    val (point, fillValue) = pointAndFillValue
                    points[point] = fillValue
                    tickLiveData.value = 0L
                }
        }
    }
}