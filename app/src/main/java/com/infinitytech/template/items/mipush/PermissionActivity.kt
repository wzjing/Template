package com.infinitytech.template.items.mipush

import android.os.Bundle
import com.infinitytech.template.App
import com.infinitytech.template.BaseActivity
import com.infinitytech.template.R
import kotlinx.android.synthetic.main.activity_permission.*

class PermissionActivity : BaseActivity() {

    companion object {
        public val ARG_PERMISSONS = "MiPushPermissions"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        intent.getStringArrayExtra(ARG_PERMISSONS)
        confirmBtn.setOnClickListener {
            withPermission(*intent.getStringArrayExtra(ARG_PERMISSONS)) {
                App.reInitMiPush(this)
                finish()
            }
        }
        cancelBtn.setOnClickListener {
            finish()
        }
    }
}
