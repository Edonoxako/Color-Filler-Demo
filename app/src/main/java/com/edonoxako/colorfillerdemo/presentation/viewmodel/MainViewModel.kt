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

class MainViewModel(
    private val colorFillerInteractor: ColorFillerInteractor,
    private val rxSchedulers: RxSchedulers
) : BaseViewModel() {

    companion object {
        private val DEFAULT_SIZE = Size(100, 100)
    }

    val firstAlgorithmOutput: LiveData<Point>
        get() = _firstAlgorithmOutput

    val secondAlgorithmOutput: LiveData<Point>
        get() = _secondAlgorithmOutput

    private val _firstAlgorithmOutput = MutableLiveData<Point>()
    private val _secondAlgorithmOutput = MutableLiveData<Point>()

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

    fun generatePoints() {
        TODO()
    }

    fun updateAlgorithmSpeed(percent: Int) {
        TODO()
    }

    fun start(startingPoint: Point) {
        dispose()
        runAlgorithm(_firstAlgorithmOutput, firstAlgorithmName, startingPoint)
        runAlgorithm(_secondAlgorithmOutput, secondAlgorithmName, startingPoint)
    }

    private fun runAlgorithm(
        outputLiveData: MutableLiveData<Point>,
        algorithmName: AlgorithmName,
        startingPoint: Point
    ) {
        safeSubscribe {
            colorFillerInteractor.run(algorithmName, startingPoint)
                .subscribeOn(rxSchedulers.computation)
                .observeOn(rxSchedulers.mainThread)
                .subscribeLoggingError { point ->
                    outputLiveData.value = point
                }
        }
    }
}