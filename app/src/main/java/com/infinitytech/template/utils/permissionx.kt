package com.infinitytech.template.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat

interface IPermissionX<T : Activity> {

    var delegateRequestCode: Int

    var snips: HashMap<Int, (success: Boolean) -> Unit>
    var delegatePermissions: HashMap<Int, Array<out String>>

    fun T.withPermission(vararg permissions: String, requestCode: Int = 0x110,
                         init: (success: Boolean) -> Unit) {
        val unGranted = permissions.filter {
            ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
        if (unGranted.isEmpty()) {
            init(true)
        } else {
            ActivityCompat.requestPermissions(this, unGranted, requestCode)
            delegateRequestCode = requestCode
            snips[requestCode] = init
            delegatePermissions[requestCode] = unGranted
        }
    }

    fun T.permissionDelegate(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == delegateRequestCode) {
            var result = true
            delegatePermissions[requestCode]?.forEach { requestPermission ->
                val index = permissions.indexOf(requestPermission)
                Log.w("Permission", "Result: $result grantResult[$index]: ${grantResults[index]}")
                result = result && index >= 0 && index < grantResults.size
                        && (grantResults[index] == PackageManager.PERMISSION_GRANTED)
            }
            snips[requestCode]?.invoke(result)
        }
    }
}

