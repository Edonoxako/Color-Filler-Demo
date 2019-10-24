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

    val generatedPoints: LiveData<Map<Point, Boolean>>
        get() = _generatedPoints

    val firstAlgorithmOutput: MutableLiveData<Map<Point, Boolean>>
        get() = _firstAlgorithmOutput

    val secondAlgorithmOutput: MutableLiveData<Map<Point, Boolean>>
        get() = _secondAlgorithmOutput

    private val _generatedPoints = MutableLiveData<Map<Point, Boolean>>()
    private val _firstAlgorithmOutput = MutableLiveData<Map<Point, Boolean>>()
    private val _secondAlgorithmOutput = MutableLiveData<Map<Point, Boolean>>()

    var imageSize = DEFAULT_SIZE
        set(value) {
            dispose()
            field = value
        }

    var firstAlgorithmName = AlgorithmName.BFS
        set(value) {
            dispose()
            field = value
        }

    var secondAlgorithmName = AlgorithmName.DFS
        set(value) {
            dispose()
            field = value
        }

    init {
        generatePoints()
    }

    fun generatePoints() {
        dispose()
        colorFillerInteractor.generatePoints(imageSize)
            .subscribeOn(rxSchedulers.computation)
            .observeOn(rxSchedulers.mainThread)
            .subscribeLoggingError { points ->
                _generatedPoints.value = points
            }
    }

    fun updateAlgorithmSpeed(percent: Int) {
        colorFillerInteractor.updateSpeed(percent)
    }

    fun start(startingPoint: Point) {
        Timber.d("Start $startingPoint")
        dispose()
        runAlgorithm("first", _firstAlgorithmOutput, firstAlgorithmName, startingPoint)
        runAlgorithm("second", _secondAlgorithmOutput, secondAlgorithmName, startingPoint)
    }

    private fun runAlgorithm(
        key: String,
        outputLiveData: MutableLiveData<Map<Point, Boolean>>,
        algorithmName: AlgorithmName,
        startingPoint: Point
    ) {
        safeSubscribe {
            colorFillerInteractor.run(key, algorithmName, startingPoint)
                .subscribeOn(rxSchedulers.computation)
                .observeOn(rxSchedulers.mainThread)
                .subscribeLoggingError { points ->
                    outputLiveData.value = points
                }
        }
    }
}