package com.edonoxako.colorfillerdemo.presentation.ui

import android.widget.SeekBar

interface SimpleSeekBarChangeListener : SeekBar.OnSeekBarChangeListener {

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}