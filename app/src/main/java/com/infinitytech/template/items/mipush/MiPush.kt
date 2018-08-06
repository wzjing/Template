package com.infinitytech.template.items.mipush

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.infinitytech.template.utils.*
import com.xiaomi.mipush.sdk.*

class MiPushReceiver : PushMessageReceiver() {

    private var mRegId: String? = null
    private var mTopic: String? = null
    private var mAlias: String? = null
    private var mAccount: String? = null
    private var mStartTime: String? = null
    private var mEndTime: String? = null

    override fun onReceivePassThroughMessage(context: Context, message: MiPushMessage) {
        logv("MiPush PassThroughMessage: $message")
        mTopic = message.topic
        mAlias = message.alias
        logd("PassThroughMessage: $message")
        context.apply {
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(0x102,
                    buildHeadUpNotification("MiPush Message", message.content, Channels.CHANNEL_MESSAGE, 0x102))
        }
    }

    override fun onNotificationMessageArrived(context: Context, message: MiPushMessage) {
        logv("NotificationMessage Arrived: $message")
    }

    override fun onNotificationMessageClicked(context: Context, message: MiPushMessage) {
        logv("NotificationMessage Clicked: $message")
        context.startActivity<MiPushActivity>(
                args = *arrayOf(MiPushActivity.ARG_MESSAGE to Bundle().apply {
                    putString(MiPushActivity.BUNDLE_ARG_TITLE, message.title)
                    putString(MiPushActivity.BUNDLE_ARG_TOPIC, message.topic)
                    putString(MiPushActivity.BUNDLE_ARG_MESSAGE, message.description)
                }))
    }

    override fun onCommandResult(context: Context, commandMessage: MiPushCommandMessage) {
        val arg1 = commandMessage.commandArguments?.getOrNull(0)
        val arg2 = commandMessage.commandArguments?.getOrNull(1)
        when (commandMessage.command) {
            MiPushClient.COMMAND_REGISTER -> {
                if (commandMessage.resultCode.toInt() == ErrorCode.SUCCESS) {
                    mRegId = arg1
                    logd("Register MiPush Success")
                } else {
                    loge("Register MiPush Failed (${commandMessage.resultCode})")
                }
            }
            MiPushClient.COMMAND_SET_ALIAS -> {
                if (commandMessage.resultCode.toInt() == ErrorCode.SUCCESS) {
                    mAlias = arg1
                    logd("Set MiPush Alias Success")
                } else {
                    loge("Set MiPush Alias Failed (${commandMessage.resultCode})")
                }
            }
            MiPushClient.COMMAND_UNSET_ALIAS -> {
                if (commandMessage.resultCode.toInt() == ErrorCode.SUCCESS) {
                    mAlias = arg1
                    logd("Unset MiPush Alias Success")
                } else {
                    loge("Unset MiPush Alias Failed (${commandMessage.resultCode})")
                }
            }
            MiPushClient.COMMAND_SET_ACCOUNT -> {
                if (commandMessage.resultCode.toInt() == ErrorCode.SUCCESS) {
                    mAccount = arg1
                    logd("Set Account Success")
                } else {
                    loge("Set Account Failed (${commandMessage.resultCode})")
                }
            }
            MiPushClient.COMMAND_UNSET_ACCOUNT -> {
                if (commandMessage.resultCode.toInt() == ErrorCode.SUCCESS) {
                    mAccount = arg1
                    logd("Unset Account Success")
                } else {
                    loge("Unset Account Failed (${commandMessage.resultCode})")
                }
            }
            MiPushClient.COMMAND_SUBSCRIBE_TOPIC -> {
                if (commandMessage.resultCode.toInt() == ErrorCode.SUCCESS) {
                    mTopic = arg1
                    logd("Subscribe Topic Success")
                } else {
                    loge("Subscribe Topic Failed (${commandMessage.resultCode})")
                }
            }
            MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC -> {
                if (commandMessage.resultCode.toInt() == ErrorCode.SUCCESS) {
                    mTopic = arg1
                    logd("Unsubscribe Topic Success")
                } else {
                    loge("Unsubscribe Topic Failed (${commandMessage.resultCode})")
                }
            }
            MiPushClient.COMMAND_SET_ACCEPT_TIME -> {
                if (commandMessage.resultCode.toInt() == ErrorCode.SUCCESS) {
                    mStartTime = arg1
                    mEndTime = arg2
                    logd("Set Accept Time Success")
                } else {
                    loge("Set Accept Time Failed (${commandMessage.resultCode})")
                }
            }
            else -> {
                loge("OnReceiveCommand: ${commandMessage.reason ?: "Unknown MiPush Error"}")
            }

        }
    }

    override fun onReceiveRegisterResult(context: Context, commandMessage: MiPushCommandMessage) {
        if (commandMessage.command == MiPushClient.COMMAND_REGISTER) {
            if (commandMessage.resultCode.toInt() == ErrorCode.SUCCESS) {
                mRegId = commandMessage.commandArguments.getOrNull(0)
                logd("ReceiveRegisterResult: Register MiPush Success")
            } else {
                loge("ReceiveRegisterResult: Register MiPush Fail")
            }
        } else {
            loge("ReceiveRegisterResult ${commandMessage.reason ?: "UnKnown MiPush Error"}")
        }
    }

    override fun onRequirePermissions(context: Context, permissions: Array<out String>) {
        context.startActivity<PermissionActivity>(
                args = *arrayOf(PermissionActivity.ARG_PERMISSONS to permissions),
                flags = Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}