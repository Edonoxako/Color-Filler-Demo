package com.edonoxako.colorfillerdemo.presentation.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.edonoxako.colorfillerdemo.domain.model.Size

class PointsRenderingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { color = Color.BLACK }

    private var mapWidth: Int? = null
    private var mapHeight: Int? = null
    private var points: MutableMap<Point, Boolean>? = null

    private val isInitialized: Boolean
        get() = mapWidth != null && mapHeight != null && points != null

    private val stepX: Float
        get() = width / mapWidth!!.toFloat()

    private val stepY: Float
        get() = height / mapHeight!!.toFloat()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!isInitialized) return

        val stepX = stepX
        val stepY = stepY

        points!!.forEach { (point, isFilled) ->
            if (isFilled) {
                canvas.drawRect(
                    point.x * stepX,
                    point.y * stepY,
                    point.x.inc() * stepX,
                    point.y.inc() * stepY,
                    paint
                )
            }
        }
    }

    fun init(size: Size, points: Map<Point, Boolean>) {
        this.mapWidth = size.width
        this.mapHeight = size.height
        this.points = points.toMutableMap()
    }

    fun togglePoint(point: Point) {
        points?.getValue(point)
            ?.not()
            ?.let { points?.set(point, it) }
        refresh()
    }

    fun refresh() = invalidate()

    fun setOnPointClickListener(listener: (Point) -> Unit) = setOnTouchListener { _, event ->
        if (event.action != MotionEvent.ACTION_DOWN) return@setOnTouchListener false

        val point = Point(
            (event.x / stepX).toInt(),
            (event.y / stepY).toInt()
        )

        listener.invoke(point)
        return@setOnTouchListener true
    }
}