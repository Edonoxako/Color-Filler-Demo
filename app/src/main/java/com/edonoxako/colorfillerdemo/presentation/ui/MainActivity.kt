package com.edonoxako.colorfillerdemo.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.edonoxako.colorfillerdemo.R
import com.edonoxako.colorfillerdemo.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_size.setOnClickListener {
            SelectSizePopupDialog.show(
                supportFragmentManager
            )
        }

        viewModel.points.observe(this) {

        }
    }
}
