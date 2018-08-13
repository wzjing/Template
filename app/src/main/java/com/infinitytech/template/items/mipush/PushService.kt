package com.infinitytech.template.items.mipush

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.infinitytech.template.MainActivity
import com.infinitytech.template.R
import com.infinitytech.template.utils.buildServiceNotification
import com.infinitytech.template.utils.logd
import com.infinitytech.template.utils.minApi

class PushService : Service() {

    companion object {
        var runSignal: Boolean = true
        fun stopService(context: Context) {
            val af = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            af.getRunningServices(100).forEach {
                if (it.service.className.contains(PushService::class.java.simpleName)) {
                    logd("""
                        ClientPackage: ${it.clientPackage}
                        ClassName:     ${it.service.className}
                        Process:       ${it.process}
                        Foreground:    ${it.foreground}
                        Running:       ${it.started}
                        """)
                }

            }
        }
    }

    private val NOTIFICATION_ID = 0x101

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, buildServiceNotification<MainActivity>("Push"))
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
}
