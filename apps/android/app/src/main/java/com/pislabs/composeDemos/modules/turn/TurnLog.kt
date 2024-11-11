package com.pislabs.composeDemos.modules.turn

import android.util.Log

/**
 * TurnLog
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/6 16:54
 */
object TurnLog {
    @JvmStatic
    var debug = false

    var TAG = "lego-zrn"

    @JvmStatic
    fun d(msg: String) {
        if (debug) Log.d(TAG, msg)
//        ZRN.logAdapter?.d(msg)
    }

    @JvmStatic
    fun e(msg: String) {
        e(msg, null)
    }

    @JvmStatic
    fun e(msg: String, e: Throwable?) {
        if (debug) Log.e(TAG, msg)
//        ZRN.logAdapter?.e(msg)
    }
}
