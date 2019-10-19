package com.edonoxako.colorfillerdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.view_size.*

class SelectSizePopupDialog : BottomSheetDialogFragment() {

    companion object {

        private const val TAG = "popup-select"

        fun show(fragmentManager: FragmentManager) {
            SelectSizePopupDialog().show(fragmentManager, TAG)
        }
    }

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
}