package com.iot.shieldsecuritysystem

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.iot.shieldsecuritysystem.databinding.ActivityMainBinding
import com.iot.shieldsecuritysystem.models.SecurityViewModel
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var securityViewModel: SecurityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        activityMainBinding.lifecycleOwner = this

        securityViewModel = SecurityViewModel()

        activityMainBinding.mainModel = securityViewModel
        activityMainBinding.mainActivityClickHandler = MainActivityClickHandlers(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 111) {
            if (data != null) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                val status = securityViewModel.securitySystemStatus.value

                if (status != null) {
                    securityViewModel.updateSecuritySystemStatus(!status)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Something Went Wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun showCodeInputBottomSheet(purpose: String, position: Int) {
        val customCodeDialogBox = CustomCodeDialogBox(this@MainActivity, purpose, position)
        customCodeDialogBox.showDialog()
    }

    fun showVoiceInputDialog() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak")
        try {
            startActivityForResult(intent, 111)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                applicationContext,
                "Sorry your device not supported",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    inner class MainActivityClickHandlers(private val context: Context) {

        lateinit var dialogTitleText: String
        lateinit var firstTextViewText: String
        lateinit var secondTextViewText: String

        fun securityBtnClickHandlers(view: View) {
            securityViewModel.securitySystemStatus.value?.let {
                dialogTitleText = if (it) {
                    getString(R.string.de_activate_by)
                } else {
                    getString(R.string.activate_by)
                }

                firstTextViewText = getString(R.string.enter_pass_code)
                secondTextViewText = getString(R.string.voice_code)

                val customDialogBox = CustomDialogBox(
                    this@MainActivity,
                    this@MainActivity,
                    dialogTitleText,
                    firstTextViewText,
                    secondTextViewText,
                    "security"
                )
                customDialogBox.showDialog()
            }
        }

        fun changePassCodeBtnClickHandlers(view: View) {
            securityViewModel.securitySystemStatus.value?.let {
                dialogTitleText = getString(R.string.change_pass_code)

                firstTextViewText = getString(R.string.activate_pass_code)
                secondTextViewText = getString(R.string.de_activate_pass_code)

                val customDialogBox = CustomDialogBox(
                    this@MainActivity,
                    this@MainActivity,
                    dialogTitleText,
                    firstTextViewText,
                    secondTextViewText,
                    "change"
                )
                customDialogBox.showDialog()
            }
        }

        fun alertOnClickHandler(view: View) {
            val status = securityViewModel.alert.value

            if (status != null) {
                securityViewModel.updateAlertStatus(!status)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Something Went Wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fun openLiveVideoActivity(view: View) {
            Log.i("VideoUrl", securityViewModel.cameraServerUrl.value.toString())

            val intent = Intent(context, LiveVideoActivity::class.java)
            intent.putExtra("url", securityViewModel.cameraServerUrl.value.toString())
            startActivity(intent)
        }
    }
}