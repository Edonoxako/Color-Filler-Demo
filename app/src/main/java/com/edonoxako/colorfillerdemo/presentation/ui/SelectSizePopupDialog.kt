package com.edonoxako.colorfillerdemo.presentation.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.edonoxako.colorfillerdemo.R
import com.edonoxako.colorfillerdemo.domain.model.Size
import com.edonoxako.colorfillerdemo.presentation.viewmodel.MainViewModel
import com.edonoxako.colorfillerdemo.common.showSoftKeyboard
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.view_size.*

class SelectSizePopupDialog : BottomSheetDialogFragment() {

    companion object {

        private const val TAG = "popup-select"

        fun show(fragmentManager: FragmentManager) {
            SelectSizePopupDialog().show(fragmentManager, TAG)
        }
    }

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_size, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_text_with.showSoftKeyboard()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        updateSize()
    }

    private fun updateSize() {
        val width = edit_text_with.text?.toString() ?: "0"
        val height = edit_text_height.text?.toString() ?: "0"
        mainViewModel.imageSize = Size(
            width = width.toInt(),
            height = height.toInt()
        )
    }
}