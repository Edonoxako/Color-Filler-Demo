package com.edonoxako.colorfillerdemo.presentation.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.edonoxako.colorfillerdemo.R
import com.edonoxako.colorfillerdemo.domain.AlgorithmName
import com.edonoxako.colorfillerdemo.presentation.viewmodel.MainViewModel
import com.jakewharton.rxbinding3.widget.changes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpSelectSizeButton()
        setUpGenerateButton()

        setUpSpeedSeekBar()

        setUpFillerView(filler_view_first)
        setUpFillerView(filler_view_second)
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
            .subscribe(viewModel::updateAlgorithmSpeed)
    }

    private fun setUpFillerView(colorFillerView: ColorFillerView) {
        with(colorFillerView) {
            algorithmChangedListener = { algorithmName -> listenToAlgorithmUpdates(algorithmName) }
            tapListener = viewModel::start
        }
    }

    private fun ColorFillerView.listenToAlgorithmUpdates(
        algorithmName: AlgorithmName
    ) {
        viewModel.algorithmOutput(algorithmName)
            .observe(this@MainActivity, this::addPoint)
    }
}
