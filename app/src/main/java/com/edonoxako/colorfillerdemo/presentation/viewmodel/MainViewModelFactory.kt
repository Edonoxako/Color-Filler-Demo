package com.edonoxako.colorfillerdemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edonoxako.colorfillerdemo.common.RxSchedulersImpl
import com.edonoxako.colorfillerdemo.data.InMemoryPointsRepository
import com.edonoxako.colorfillerdemo.domain.ColorFillerInteractor
import com.edonoxako.colorfillerdemo.domain.PointsGenerator
import com.edonoxako.colorfillerdemo.domain.algorithm.FillAlgorithmFactory

class MainViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            colorFillerInteractor = ColorFillerInteractor(
                fillAlgorithmFactory = FillAlgorithmFactory(),
                pointsRepository = InMemoryPointsRepository(),
                pointsGenerator = PointsGenerator()
            ),
            rxSchedulers = RxSchedulersImpl()
        ) as T
    }
}