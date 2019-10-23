package com.edonoxako.colorfillerdemo.presentation.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.edonoxako.colorfillerdemo.R
import com.edonoxako.colorfillerdemo.presentation.viewmodel.MainViewModel
import com.edonoxako.colorfillerdemo.presentation.viewmodel.MainViewModelFactory
import com.jakewharton.rxbinding3.widget.changes
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> { MainViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpSelectSizeButton()
        setUpGenerateButton()

        setUpSpeedSeekBar()

        setUpFirstColorFillerView()
        setUpSecondColorFillerView()
    }

    private fun setUpSelectSizeButton() {
        button_size.setOnClickListener {
            SelectSizePopupDialog.show(supportFragmentManager)
        }
    }

    private fun setUpGenerateButton() {
        button_generate.setOnClickListener {
            viewModel.generatePoints()
        }
    }

    @SuppressLint("CheckResult")
    private fun setUpSpeedSeekBar() {
        seek_bar_speed.changes()
            .debounce(100, TimeUnit.MILLISECONDS)
            .subscribe(viewModel::updateAlgorithmSpeed, Timber::e)
    }

    private fun setUpFirstColorFillerView() {
        with(filler_view_first) {
            selectedAlgorithm = viewModel.firstAlgorithmName
            algorithmChangedListener = { algorithmName ->
                viewModel.firstAlgorithmName = algorithmName
            }
            tapListener = viewModel::start
            viewModel.firstAlgorithmOutput.observe(this@MainActivity, this::updatePoints)
            viewModel.generatedPoints.observe(this@MainActivity) { points ->
                addAllPoints(viewModel.imageSize, points)
            }
        }
    }

    private fun setUpSecondColorFillerView() {
        with(filler_view_second) {
            selectedAlgorithm = viewModel.secondAlgorithmName
            algorithmChangedListener = { algorithmName ->
                viewModel.secondAlgorithmName = algorithmName
            }
            tapListener = viewModel::start
            viewModel.secondAlgorithmOutput.observe(this@MainActivity, this::updatePoints)
            viewModel.generatedPoints.observe(this@MainActivity) { points ->
                addAllPoints(viewModel.imageSize, points)
            }
        }
    }
}
