package com.infinitytech.template.items.mipush

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.infinitytech.template.R
import com.infinitytech.template.utils.logd
import com.infinitytech.template.utils.minApi

class PushService : Service() {

    private var runSignal: Boolean = true
    private val CHANNEL_ID = "Push"
    private val NOTIFICATION_ID = 0x202

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, buildNotification())
        Thread {
            runSignal = true
            while (runSignal) {
                logd("Push Service is running...")
                Thread.sleep(1000)
            }
        }.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        runSignal = false
    }


    private fun buildNotification(): Notification {
        minApi(26) {
            val channel = NotificationChannel(CHANNEL_ID, "Push Channel", NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setSmallIcon(R.drawable.mipush_small_notification)
        builder.setContentTitle("Push")
        builder.setContentText("Background push service is running")
        builder.setVisibility(Notification.VISIBILITY_PUBLIC)
        minApi(24) {
            builder.priority = NotificationManager.IMPORTANCE_HIGH
        }
        return builder.build()
    }
}
