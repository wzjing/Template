@file:Suppress("unused")

package com.infinitytech.template.utils

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment

inline fun <reified T: Activity> T.v(msg: String) = Log.v(T::class.java.simpleName, msg)
inline fun <reified T: Activity> T.d(msg: String) = Log.d(T::class.java.simpleName, msg)
inline fun <reified T: Activity> T.i(msg: String) = Log.i(T::class.java.simpleName, msg)
inline fun <reified T: Activity> T.w(msg: String) = Log.w(T::class.java.simpleName, msg)
inline fun <reified T: Activity> T.e(msg: String) = Log.e(T::class.java.simpleName, msg)

inline fun <reified T: Fragment> T.v(msg: String) = Log.v(T::class.java.simpleName, msg)
inline fun <reified T: Fragment> T.d(msg: String) = Log.d(T::class.java.simpleName, msg)
inline fun <reified T: Fragment> T.i(msg: String) = Log.i(T::class.java.simpleName, msg)
inline fun <reified T: Fragment> T.w(msg: String) = Log.w(T::class.java.simpleName, msg)
inline fun <reified T: Fragment> T.e(msg: String) = Log.e(T::class.java.simpleName, msg)

inline fun <reified T> T.logv(msg: String) = Log.v(T::class.java.simpleName, msg)
inline fun <reified T> T.logd(msg: String) = Log.d(T::class.java.simpleName, msg)
inline fun <reified T> T.logi(msg: String) = Log.i(T::class.java.simpleName, msg)
inline fun <reified T> T.logw(msg: String) = Log.w(T::class.java.simpleName, msg)
inline fun <reified T> T.loge(msg: String) = Log.e(T::class.java.simpleName, msg)