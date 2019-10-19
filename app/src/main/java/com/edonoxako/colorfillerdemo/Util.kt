package com.edonoxako.colorfillerdemo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes

fun ViewGroup.inflateSelf(@LayoutRes layoutRes: Int) {
    View.inflate(context, layoutRes, this)
}

fun View.showSoftKeyboard() {
    post {
        if (this.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}