package com.edonoxako.colorfillerdemo.presentation.viewmodel

import android.graphics.Point
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edonoxako.colorfillerdemo.domain.AlgorithmName
import io.reactivex.Flowable

class MainViewModel : ViewModel() {

    val points: LiveData<List<Point>>
        get() = _points

    private val _points = MutableLiveData<List<Point>>().apply {
        value = listOf(
            Point(10, 10),
            Point(20, 20),
            Point(30, 30),
            Point(40, 40),
            Point(50, 50)
        )
    }

    fun getPointsStream(id: Int): LiveData<Point> {
        TODO()
    }

    fun generatePoints() {
        TODO()
    }

    fun updateImageSize() {
        TODO()
    }

    fun updateAlgorithmSpeed() {
        TODO()
    }

    fun start(startingPoint: Point) {
        TODO()
    }

    fun selectAlgorithm(id: Int, algorithmName: AlgorithmName) {
        TODO()
    }
}