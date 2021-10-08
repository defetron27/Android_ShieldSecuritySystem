package com.iot.shieldsecuritysystem.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class SecurityViewModel : ViewModel() {
    val activatePassCode: MutableLiveData<Long> = MutableLiveData()
    val activateVoiceCode: MutableLiveData<String> = MutableLiveData()
    val alert: MutableLiveData<Boolean> = MutableLiveData()
    val cameraServerUrl: MutableLiveData<String> = MutableLiveData()
    val deActivatePassCode: MutableLiveData<Long> = MutableLiveData()
    val deActivateVoiceCode: MutableLiveData<String> = MutableLiveData()
    val securitySystemStatus: MutableLiveData<Boolean> = MutableLiveData()

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("Security")

    init {
        setData()
    }

    private fun setData() {

        databaseReference.get()

        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.i("databaseLis", snapshot.value.toString())
                Log.i("databaseLis", snapshot.key.toString())

                val key =  snapshot.key
                val value = snapshot.value

                /*if (key == "security_system_status") {
                    securitySystemStatus.postValue(value as Boolean)
                }

                if (key == "camera_server_url") {
                    cameraServerUrl.postValue(value as String)
                }

                if (key == "alert") {
                    alert.postValue(value as Boolean)
                }

                if (key == "activate_pass_code") {
                    activatePassCode.postValue(value as Long)
                }

                if (key == "deactivate_pass_code") {
                    deActivatePassCode.postValue(value as Long)
                }

                if (key == "activate_voice_code") {
                    activateVoiceCode.postValue(value as String)
                }

                if (key == "deactivate_voice_code") {
                    deActivateVoiceCode.postValue(value as String)
                }*/

                if (key == "security_system_status") {
                    Log.i("securityStatus", key.toString())
                    Log.i("securityStatus", value.toString())
                    securitySystemStatus.value = value as Boolean
                    Log.i("securityStatus", securitySystemStatus.value.toString())
                }

                if (key == "camera_server_url") {
//                    Log.i("VideoUrl", key.toString())
//                    Log.i("VideoUrl", value.toString())
                    cameraServerUrl.value = value.toString()
//                    Log.i("VideoUrl", cameraServerUrl.value.toString())
                }

                if (key == "alert") {
                    alert.value = value as Boolean
                }

                if (key == "activate_pass_code") {
                    activatePassCode.value = value as Long
                }

                if (key == "deactivate_pass_code") {
                    deActivatePassCode.value = value as Long
                }

                if (key == "activate_voice_code") {
                    activateVoiceCode.value = value.toString()
                }

                if (key == "deactivate_voice_code") {
                    deActivateVoiceCode.value = value.toString()
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.i("details", snapshot.value.toString())
                Log.i("databaseLis", "onChildChanged")

                val key =  snapshot.key
                val value = snapshot.value

                if (key == "security_system_status") {
                    Log.i("securityStatus", key.toString())
                    Log.i("securityStatus", value.toString())
                    securitySystemStatus.value = value as Boolean
                    Log.i("securityStatus", securitySystemStatus.value.toString())
                }

                if (key == "camera_server_url") {
//                    Log.i("VideoUrl", key.toString())
//                    Log.i("VideoUrl", value.toString())
                    cameraServerUrl.value = value.toString()
//                    Log.i("VideoUrl", cameraServerUrl.value.toString())
                }

                if (key == "alert") {
                    alert.value = value as Boolean
                }

                if (key == "activate_pass_code") {
                    activatePassCode.value = value as Long
                }

                if (key == "deactivate_pass_code") {
                    deActivatePassCode.value = value as Long
                }

                if (key == "activate_voice_code") {
                    activateVoiceCode.value = value.toString()
                }

                if (key == "deactivate_voice_code") {
                    deActivateVoiceCode.value = value.toString()
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.i("databaseLis", "onChildRemoved")
                Log.i("details", snapshot.value.toString())
                Log.i("details", snapshot.key.toString())
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.i("databaseLis", "onCancelled")

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("databaseLis", "onCancelled")

            }
        })
    }

    fun updateServoAngle(angleValue: Int) {
        databaseReference.updateChildren(mapOf("servo_angle" to angleValue))
    }

    fun updateSecuritySystemStatus(status: Boolean) {
        databaseReference.updateChildren(mapOf("security_system_status" to status))
    }

    fun updateAlertStatus(status: Boolean) {
        databaseReference.updateChildren(mapOf("alert" to status))
    }
}
