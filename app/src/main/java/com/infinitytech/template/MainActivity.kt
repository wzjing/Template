package com.infinitytech.template

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.infinitytech.template.items.CameraActivity
import com.infinitytech.template.items.FileSelectorActivity
import com.infinitytech.template.items.IOActivity
import com.infinitytech.template.items.mipush.MiPushActivity
import com.infinitytech.template.utils.startActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item1.setOnClickListener { startActivity<CameraActivity>() }
        item2.setOnClickListener { startActivity<IOActivity>() }
        item3.setOnClickListener { startActivity<FileSelectorActivity>() }
        item4.setOnClickListener { startActivity<MiPushActivity>() }
    }

}