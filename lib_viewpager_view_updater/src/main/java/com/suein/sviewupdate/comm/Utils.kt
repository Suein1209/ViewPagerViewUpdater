package com.suein.sviewupdate.comm

import android.util.Log

/**
 * View pager updater utils
 * Email : suein1209@gmail.com
 * Created by suein1209 on 2018. 8. 6..
 */

/**
 * If value is null, call callback and return null.
 * does not throw [IllegalStateException]
 */
internal inline fun <T : Any> checkNotNullSafety(value: T?, nullCallBack: () -> Unit): T? {
    return if (value == null) {
        nullCallBack.invoke()
        null
    } else {
        value
    }
}

internal object SLog {
    private val LOG_TAG = "SViewUpdate"
    var isEnableLogging = true
    var isEnableErrorLogging = true


    fun i(msg: String) {
        if (isEnableLogging && !isEnableErrorLogging)
            Log.i(LOG_TAG, msg)
    }

    fun d(msg: String) {
        if (isEnableLogging && !isEnableErrorLogging)
            Log.d(LOG_TAG, msg)
    }

    fun e(msg: String) {
        if (isEnableLogging || isEnableErrorLogging)
            Log.e(LOG_TAG, msg)
    }
}
