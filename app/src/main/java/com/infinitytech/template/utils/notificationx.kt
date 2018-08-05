package com.infinitytech.template.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

enum class Channels(val channelId: String, val channelName: String) {
    CHANNEL_MESSAGE("channel_message", "Message"),
    CHANNEL_BACKGROUND_TASK("channel_background_task", "Background Task")
}

fun Context.buildHeadUpNotification(title: String,
                                    message: String,
                                    channel: Channels,
                                    action1: () -> Unit,
                                    action2: () -> Unit) {
    minApi(26) {
        val notificationChannel = NotificationChannel(channel.channelId, channel.channelName, NotificationManager.IMPORTANCE_HIGH)
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(notificationChannel)
    }
    val builder = NotificationCompat.Builder(this, channel.channelId)
    builder.setContentTitle(title)
    builder.setContentText(message)
}