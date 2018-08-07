package com.infinitytech.template.items.mipush

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class OrderService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val notificationId = intent.getIntExtra("notification_id", -1)
            if (notificationId > 0) {
                (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                        .cancel(notificationId)
            }
        }

        return Service.START_STICKY
    }
}
