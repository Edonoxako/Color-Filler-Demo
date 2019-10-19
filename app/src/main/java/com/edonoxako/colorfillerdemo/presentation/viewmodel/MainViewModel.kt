package com.edonoxako.colorfillerdemo.presentation.viewmodel

import android.graphics.Point
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edonoxako.colorfillerdemo.domain.AlgorithmName
import com.edonoxako.colorfillerdemo.domain.model.Size
import io.reactivex.Flowable

class MainViewModel : ViewModel() {

    fun algorithmOutput(algorithmName: AlgorithmName): LiveData<Point> {
        TODO()
    }

    fun generatePoints() {
        TODO()
    }

    fun updateImageSize(size: Size) {
        TODO()
    }

    fun updateAlgorithmSpeed(percent: Int) {
        TODO()
    }

    fun start(startingPoint: Point) {
        TODO()
    }
}