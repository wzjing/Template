package com.infinitytech.template.utils

import android.os.Build

fun minApi(minVersion: Int, init: ()->Unit) {
    if (Build.VERSION.SDK_INT >= minVersion) init()
}