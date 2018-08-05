package com.infinitytech.template.items.mipush

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.infinitytech.template.App
import com.infinitytech.template.BaseActivity
import com.infinitytech.template.R
import kotlinx.android.synthetic.main.activity_mi_push.*
import kotlinx.android.synthetic.main.activity_permission.*

class MiPushActivity : BaseActivity() {

    companion object {
        public val ARG_MESSAGE = "argument_message"
        public val BUNDLE_ARG_TITLE = "bundle_arg_title"
        public val BUNDLE_ARG_TOPIC = "bundle_arg_topic"
        public val BUNDLE_ARG_MESSAGE = "bundle_arg_message"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_push)
        intent.getBundleExtra(ARG_MESSAGE)?.apply {
            titleTv.text = getString(BUNDLE_ARG_TITLE, "title")
            topicTv.text = getString(BUNDLE_ARG_TOPIC, "topic")
            messageTv.text = getString(BUNDLE_ARG_MESSAGE, "message")
        }

        startServiceBtn.setOnClickListener { startService(Intent(this, PushService::class.java)) }
    }
}
