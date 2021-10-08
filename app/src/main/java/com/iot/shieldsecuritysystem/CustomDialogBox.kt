package com.iot.shieldsecuritysystem

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Window
import com.google.android.material.textview.MaterialTextView

class CustomDialogBox(
    context: Context,
    activity: MainActivity,
    dialogTitleText: String,
        firstTextViewText: String,
        secondTextViewText: String,
        private val purpose: String
) {
    var dialog: Dialog = Dialog(context)

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_dialog_box)

        val dialogTitleView = dialog.findViewById<MaterialTextView>(R.id.dialog_title_view)
        val firstTextView = dialog.findViewById<MaterialTextView>(R.id.first_text_view)
        val secondTextView = dialog.findViewById<MaterialTextView>(R.id.second_text_view)

        dialogTitleView.text = dialogTitleText
        firstTextView.text = firstTextViewText
        secondTextView.text = secondTextViewText

        firstTextView.setOnClickListener {
            dialog.dismiss()
            if (purpose == "change") {
                activity.showCodeInputBottomSheet(purpose, 1)
            } else {
                activity.showCodeInputBottomSheet(purpose, 0)
            }
        }

        secondTextView.setOnClickListener {
            dialog.dismiss()
            if (purpose == "change") {
                activity.showCodeInputBottomSheet(purpose, 2)
            } else {
                activity.showVoiceInputDialog()
            }
        }
    }

    fun showDialog() {
        dialog.show()
    }
}