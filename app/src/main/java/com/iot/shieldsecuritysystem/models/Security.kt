package com.iot.shieldsecuritysystem.models

data class Security(
    val activate_pass_code: Int,
    val activate_voice_code: String,
    val alert: Boolean,
    val camera_server_url: String,
    val de_activate_pass_code: String,
    val security_system_status: Boolean,
    val servo_angle: Int
)