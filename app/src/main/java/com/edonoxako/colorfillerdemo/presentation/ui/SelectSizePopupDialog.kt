package com.edonoxako.colorfillerdemo.presentation.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialog
import androidx.core.app.DialogCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.edonoxako.colorfillerdemo.R
import com.edonoxako.colorfillerdemo.domain.model.Size
import com.edonoxako.colorfillerdemo.presentation.viewmodel.MainViewModel
import com.edonoxako.colorfillerdemo.common.showSoftKeyboard
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.view_size.*

class SelectSizePopupDialog : DialogFragment() {

    companion object {

        private const val TAG = "popup-select"
        private const val MAX_SIZE = 500

        fun show(fragmentManager: FragmentManager) {
            SelectSizePopupDialog().show(fragmentManager, TAG)
        }
    }

    private val compositeDisposable = CompositeDisposable()

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = FullscreenDialog(requireContext())
        val contentView = View.inflate(requireContext(), R.layout.view_size, null)
        return dialog.apply { setContentView(contentView) }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        with(dialog) {
            setUpEditTextsWithViewModel()
            edit_text_width.showSoftKeyboard()
            edit_text_height.dismissOnImeDoneAction()
        }
    }

    private fun Dialog.setUpEditTextsWithViewModel() {
        edit_text_width.setText(mainViewModel.imageSize.width.toString())
        edit_text_height.setText(mainViewModel.imageSize.height.toString())

        edit_text_width.textChanges()
            .subscribe {
                validateMaxValue(
                    it.toString(),
                    edit_text_width_layout,
                    "The width is too big"
                )
            }
            .addTo(compositeDisposable)

        edit_text_height.textChanges()
            .subscribe {
                validateMaxValue(
                    it.toString(),
                    edit_text_height_layout,
                    "The height is too big"
                )
            }
            .addTo(compositeDisposable)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val trueDialog = dialog as Dialog
        trueDialog.updateSize()
        compositeDisposable.clear()
    }

    private fun Dialog.updateSize() {
        val width = edit_text_width.text
            ?.toString()
            ?.toIntOrNull()
            ?: mainViewModel.imageSize.width

        val height = edit_text_height.text
            ?.toString()
            ?.toIntOrNull()
            ?: mainViewModel.imageSize.height

        if (width <= MAX_SIZE && height <= MAX_SIZE) {
            mainViewModel.imageSize = Size(
                width = width,
                height = height
            )
        }
    }

    private fun EditText.dismissOnImeDoneAction() {
        setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_DONE) {
                dismiss()
                true
            } else {
                false
            }
        }
    }

    private fun validateMaxValue(text: String, editTextLayout: TextInputLayout, errorText: String) {
        val value = text.toIntOrNull() ?: 0
        if (value > MAX_SIZE) {
            editTextLayout.error = errorText
            editTextLayout.isErrorEnabled = true
        } else {
            editTextLayout.isErrorEnabled = false
        }
    }
}