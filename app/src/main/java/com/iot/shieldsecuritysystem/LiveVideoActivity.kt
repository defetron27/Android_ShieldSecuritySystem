package com.iot.shieldsecuritysystem

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.slider.Slider
import com.iot.shieldsecuritysystem.databinding.ActivityLiveVideoBinding
import com.iot.shieldsecuritysystem.models.SecurityViewModel


class LiveVideoActivity : AppCompatActivity() {

    private lateinit var liveVideoModel: SecurityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityLiveVideoBinding: ActivityLiveVideoBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_live_video
        )

        activityLiveVideoBinding.lifecycleOwner = this

        liveVideoModel = SecurityViewModel()

        val bundle = intent.extras

        val url = bundle?.getString("url")

        activityLiveVideoBinding.servoAngleSlider.addOnSliderTouchListener(object :
            Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                Log.i("ServoAngle", slider.value.toInt().toString())
                liveVideoModel.updateServoAngle(slider.value.toInt())
            }
        })


        val videoView = activityLiveVideoBinding.myVideoView

        if (url != null) {
            val uri: Uri = Uri.parse(url)
            videoView.setVideoURI(uri)
            videoView.start()
        }
    }
}