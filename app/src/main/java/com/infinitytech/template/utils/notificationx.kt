package com.infinitytech.template.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.infinitytech.template.MainActivity
import com.infinitytech.template.R
import com.infinitytech.template.items.mipush.OrderService

enum class Channels(val channelId: String, val channelName: String) {
    CHANNEL_MESSAGE("channel_message", "Message"),
    CHANNEL_BACKGROUND_TASK("channel_background_task", "Background Task")
}

fun Context.sendHeadUpNotification(title: String,
                                   message: String,
                                   channel: Channels,
                                   notificationId: Int,
                                   lastTimeMillis: Long) {
    (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(notificationId,
            buildHeadUpNotification(title, message, channel, notificationId))
    (getSystemService(Context.ALARM_SERVICE) as AlarmManager).set(AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + lastTimeMillis,
            PendingIntent.getService(this, 0x312,
                    Intent(this, OrderService::class.java)
                            .putExtra("notification_id", notificationId),
                    PendingIntent.FLAG_UPDATE_CURRENT))
}

fun Context.buildHeadUpNotification(title: String,
                                    message: String,
                                    channel: Channels,
                                    notificationId: Int): Notification {
    minApi(26) {
        val notificationChannel = NotificationChannel(channel.channelId, channel.channelName,
                NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.vibrationPattern = longArrayOf(0L, 1000L, 1000L)
        notificationChannel.setSound(Uri.parse("android.resource://$packageName/${R.raw.notify_sound}"),
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build())
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(notificationChannel)
    }
    val builder = NotificationCompat.Builder(this, channel.channelId)
    builder.setContentTitle(title)
    builder.setContentText(message)
    builder.setSmallIcon(R.drawable.mipush_small_notification)
    builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
    builder.setVibrate(longArrayOf(0L, 1000L, 1000L))
    builder.setSound(Uri.parse("android.resource://$packageName/${R.raw.notify_sound}"))
    builder.priority = NotificationCompat.PRIORITY_HIGH
    builder.setFullScreenIntent(
            PendingIntent.getActivity(this, 0x311,
                    Intent(this, MainActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT), true)

    builder.setContentIntent(PendingIntent.getActivity(this, 0x311,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT))

    builder.addAction(R.drawable.ic_order_pass, "取消",
            PendingIntent.getService(this, 0x312,
                    Intent(this, OrderService::class.java)
                            .putExtra("notification_id", notificationId),
                    PendingIntent.FLAG_UPDATE_CURRENT))

    builder.addAction(R.drawable.ic_order_accept, "接单",
            PendingIntent.getService(this, 0x313,
                    Intent(this, OrderService::class.java)
                            .putExtra("notification_id", notificationId),
                    PendingIntent.FLAG_UPDATE_CURRENT))
    return builder.build().apply {
        flags = Notification.FLAG_ONGOING_EVENT or Notification.FLAG_INSISTENT or Notification.FLAG_AUTO_CANCEL
    }
}

inline fun <reified T : Activity> Context.buildServiceNotification(title: String, content: String? = null): Notification {
    minApi(26) {
        val channel = NotificationChannel(Channels.CHANNEL_BACKGROUND_TASK.channelId,
                Channels.CHANNEL_BACKGROUND_TASK.channelName, NotificationManager.IMPORTANCE_LOW)
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
    }
    val builder = NotificationCompat.Builder(this, Channels.CHANNEL_BACKGROUND_TASK.channelId)
    builder.setSmallIcon(R.drawable.mipush_small_notification)
    builder.setContentTitle(title)
    builder.setContentText(content)
    builder.setVisibility(NotificationCompat.VISIBILITY_SECRET)
    builder.priority = NotificationCompat.PRIORITY_LOW
    builder.setVibrate(longArrayOf())
    builder.setSound(null)
    val pIntent = PendingIntent.getActivity(this, 0x312,
            Intent(this, T::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
    builder.setContentIntent(pIntent)
    return builder.build().apply { flags = flags or Notification.FLAG_FOREGROUND_SERVICE }
}