package com.iot.shieldsecuritysystem

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.database.FirebaseDatabase
import com.iot.shieldsecuritysystem.models.Security
import com.iot.shieldsecuritysystem.models.SecurityDetailsModel
import com.iot.shieldsecuritysystem.models.SecurityViewModel
import java.util.function.LongFunction

class CustomCodeDialogBox(
    context: Context,
    purpose: String,
    position: Int,
) {
    var dialog: Dialog = Dialog(context)

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("Security")

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_code_dialog_box)

        val codeDialogTitleView = dialog.findViewById<MaterialTextView>(R.id.code_dialog_title_view)
        val enterCodeDialog = dialog.findViewById<TextInputLayout>(R.id.enter_code_input_layout)
        val setSecurityStatusBtn = dialog.findViewById<MaterialButton>(R.id.set_security_status_btn)

        if (purpose == "change") {
            codeDialogTitleView.text = context.getString(R.string.change_code)
            setSecurityStatusBtn.text = context.getString(R.string.change_passcode)
        }

        /*securityViewModel = SecurityViewModel()

        setSecurityStatusBtn.setOnClickListener {
            val code = enterCodeDialog.editText?.text.toString()

            if (code.isEmpty()) {
                Toast.makeText(context, "Please Enter Valid Code.", Toast.LENGTH_LONG).show()
            }

            try {
                val securityStatus = securityViewModel.securitySystemStatus.value

                securityStatus?.let { status ->
                    if (status) {

                        if (deActivateCode != null) {
                            Log.i("deactivateCode", deActivateCode.toString())

                            if (code.toLong() == deActivateCode) {
                                securityViewModel.updateSecuritySystemStatus(!status)
                            } else {
                                Toast.makeText(context, "Please Enter Valid Code.", Toast.LENGTH_LONG).show()
                            }
                        }
                        else {
                            Toast.makeText(context, "Please Enter Valid Code.", Toast.LENGTH_LONG).show()
                        }


                    } else {
                        activateCode?.let {
                            Log.i("activateCode", activateCode.toString())

                            if (code.toLong() == activateCode) {
                                securityViewModel.updateSecuritySystemStatus(!status)
                            } else {
                                Toast.makeText(context, "Please Enter Valid Code.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Please Enter Valid Code." + e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }*/

        setSecurityStatusBtn.setOnClickListener {
            val code = enterCodeDialog.editText?.text.toString()

            if (code.isEmpty()) {
                Toast.makeText(context, "Please Enter Valid Code.", Toast.LENGTH_LONG).show()
            }

            try {
                databaseReference.get().addOnSuccessListener {
//                    val securityDetails = it.getValue(Security::class.java)

                    Log.i("Details_Model", it.key.toString())

                    val details = it.value as HashMap<*, *>

                    Log.i("Details_Model", details.toString())

                    val securityStatus = details["security_system_status"] as Boolean

                    if (purpose == "security" && position == 0) {
                        if (securityStatus) {
                            val deActivateCode = details["de_activate_pass_code"] as Long

                            Log.i("PassCode", deActivateCode.toString())
                            Log.i("PassCode", code.toLong().toString())

                            if (code.toLong() == deActivateCode) {
                                databaseReference.updateChildren(mapOf("security_system_status" to !securityStatus))
                                dialog.dismiss()
                                Toast.makeText(
                                    context,
                                    "System Deactivated",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please Enter Valid Code.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            val activateCode = details["activate_pass_code"] as Long

                            if (code.toLong() == activateCode) {
                                databaseReference.updateChildren(mapOf("security_system_status" to !securityStatus))
                                dialog.dismiss()
                                Toast.makeText(
                                    context,
                                    "System Activated",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please Enter Valid Code.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                    if (purpose == "change" && position == 1) {
                        databaseReference.updateChildren(mapOf("activate_pass_code" to code.toLong()))
                        dialog.dismiss()
                        Toast.makeText(
                            context,
                            "Activate pass code has been changed",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    if (purpose == "change" && position == 2) {
                        databaseReference.updateChildren(mapOf("de_activate_pass_code" to code.toLong()))
                        dialog.dismiss()
                        Toast.makeText(
                            context,
                            "De Activate pass code has been changed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

//                val securityStatus = securityViewModel.securitySystemStatus.value
//
//                securityStatus?.let { status ->
//                    if (status) {
//
//                        if (deActivateCode != null) {
//                            Log.i("deactivateCode", deActivateCode.toString())
//
//                            if (code.toLong() == deActivateCode) {
//                                securityViewModel.updateSecuritySystemStatus(!status)
//                            } else {
//                                Toast.makeText(context, "Please Enter Valid Code.", Toast.LENGTH_LONG).show()
//                            }
//                        }
//                        else {
//                            Toast.makeText(context, "Please Enter Valid Code.", Toast.LENGTH_LONG).show()
//                        }
//
//
//                    } else {
//                        activateCode?.let {
//                            Log.i("activateCode", activateCode.toString())
//
//                            if (code.toLong() == activateCode) {
//                                securityViewModel.updateSecuritySystemStatus(!status)
//                            } else {
//                                Toast.makeText(context, "Please Enter Valid Code.", Toast.LENGTH_LONG).show()
//                            }
//                        }
//                    }
//                }
            } catch (e: Exception) {
                Toast.makeText(context, "Please Enter Valid Code." + e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun showDialog() {
        dialog.show()
    }
}