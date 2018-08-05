@file:Suppress("unused")

package com.infinitytech.template.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Activity> Context.startActivity(
        vararg args: Pair<String, Any> = emptyArray(),
        flags: Int = 0) {
    this.startActivity(buildIntent(this, T::class.java, args = *args, flags = flags))
}

inline fun <reified T : Activity> buildIntent(context: Context,
                                              cls: Class<T>,
                                              vararg args: Pair<String, Any> = emptyArray(),
                                              flags: Int) =
        Intent(context, cls).apply {
            args.forEach { (key, value) ->
                when (value) {
                    is String -> putExtra(key, value)
                    is Int -> putExtra(key, value)
                    is Long -> putExtra(key, value)
                    is Float -> putExtra(key, value)
                    is Double -> putExtra(key, value)
                    is Bundle -> putExtra(key, value)
                    is Array<*> -> putExtra(key, value)
                    is Parcelable -> putExtra(key, value)
                }
            }
            setFlags(flags)
        }