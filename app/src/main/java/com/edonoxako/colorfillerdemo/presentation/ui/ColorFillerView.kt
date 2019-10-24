package com.edonoxako.colorfillerdemo.presentation.ui

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.edonoxako.colorfillerdemo.R
import com.edonoxako.colorfillerdemo.domain.model.AlgorithmName
import com.edonoxako.colorfillerdemo.common.inflateSelf
import com.edonoxako.colorfillerdemo.domain.model.Size
import kotlinx.android.synthetic.main.view_color_filler.view.*

class ColorFillerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        inflateSelf(R.layout.view_color_filler)
        orientation = VERTICAL

        spinner_algorithm.adapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            AlgorithmName.values().map { it.rawName }
        )
    }

    var selectedAlgorithm: AlgorithmName = AlgorithmName.BFS
        set(value) {
            field = value
            AlgorithmName.values()
                .indexOfFirst { it == value }
                .let { spinner_algorithm.setSelection(it) }
        }

    var algorithmChangedListener: (algorithmName: AlgorithmName) -> Unit = {}
        set(value) {
            field = value
            spinner_algorithm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val algorithmName = AlgorithmName.values()[position]
                    value.invoke(algorithmName)
                }
            }
        }

    var tapListener: (tapPoint: Point) -> Unit = {}
        set(value) = view_points_rendering.setOnPointClickListener(value)

    fun updatePoints() {
        view_points_rendering.refresh()
    }

    fun addAllPoints(size: Size, points: Map<Point, Boolean>) {
        view_points_rendering.init(size, points)
    }
}