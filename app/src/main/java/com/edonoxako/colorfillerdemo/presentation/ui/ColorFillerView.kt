package com.edonoxako.colorfillerdemo.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.edonoxako.colorfillerdemo.R
import com.edonoxako.colorfillerdemo.inflateSelf

class ColorFillerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    init {
        inflateSelf(R.layout.view_color_filler)
        orientation = VERTICAL
    }

}