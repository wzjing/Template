package com.infinitytech.template

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import android.util.Log
import com.infinitytech.template.utils.logd
import com.infinitytech.template.utils.logw
import com.xiaomi.channel.commonutils.logger.LoggerInterface
import com.xiaomi.mipush.sdk.Logger
import com.xiaomi.mipush.sdk.MiPushClient

class App : Application() {

    companion object {
        private val APP_ID = "2882303761517845363"
        private val APP_KEY = "5501784572363"
        private val MIPUSH_TAG = "MiPush"

        public fun reInitMiPush(context: Context){
            MiPushClient.registerPush(context.applicationContext, APP_ID, APP_KEY)
        }
    }

    override fun onCreate() {
        super.onCreate()

        // MiPush Initial
        logw("Should Register: ${shouldInit()}")
        if (shouldInit()) MiPushClient.registerPush(this, APP_ID, APP_KEY)
        // MiPush Log Initial
        Logger.setLogger(this, object : LoggerInterface {
            override fun setTag(p0: String?) = Unit

            override fun log(content: String?) {
                Log.d(MIPUSH_TAG, content)
            }

            override fun log(content: String?, t: Throwable?) {
                Log.d(MIPUSH_TAG, content, t)
            }

        })
    }

    private fun shouldInit(): Boolean {
        logd("Registering MiPush")
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processInfos = am.runningAppProcesses
        val mainProcessName = packageName
        val myPid = Process.myPid()
        return processInfos.find { it.pid == myPid && it.processName == mainProcessName } != null
    }
}