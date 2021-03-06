package com.infinitytech.template

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.infinitytech.template.items.CameraActivity
import com.infinitytech.template.items.FileSelectorActivity
import com.infinitytech.template.items.IOActivity
import com.infinitytech.template.items.RecyclerViewActivity
import com.infinitytech.template.items.mipush.MiPushActivity
import com.infinitytech.template.utils.startActivity
import com.infinitytech.template.utils.w
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        w("MainActivity OnCreate")

        item1.setOnClickListener { startActivity<CameraActivity>() }
        item2.setOnClickListener { startActivity<IOActivity>() }
        item3.setOnClickListener { startActivity<FileSelectorActivity>() }
        item4.setOnClickListener { startActivity<MiPushActivity>() }
        item5.setOnClickListener { startActivity<RecyclerViewActivity>() }
    }

    override fun onResume() {
        super.onResume()
        w("MainActivity OnResume")
        val notificationId = intent.getIntExtra("notification_id", -1)
        if (notificationId > 0) (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(notificationId)
    }

}