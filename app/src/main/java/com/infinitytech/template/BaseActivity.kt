package com.infinitytech.template

import androidx.appcompat.app.AppCompatActivity
import com.infinitytech.template.utils.IPermissionX

open class BaseActivity: AppCompatActivity(), IPermissionX<BaseActivity> {
    override var delegateRequestCode: Int = 0x110
    override var delegatePermissions: HashMap<Int, Array<out String>> = hashMapOf()
    override var snips: HashMap<Int, (success: Boolean) -> Unit> = hashMapOf()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionDelegate(requestCode, permissions, grantResults)
    }
}