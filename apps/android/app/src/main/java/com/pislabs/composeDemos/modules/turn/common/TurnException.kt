package com.pislabs.composeDemos.modules.turn.common


class TurnException(var code: Int, var msg: String?) : Exception()

/**
 * TurnException
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/7 13:33
 */
object TurnErrorCode {
    const val INVALID_URL= 96

    const val EMPTY_MODULE_NAME = 97

    const val NO_DATA = 99

    const val APPKEY_EMPTY = 100

    const val QUERY_FAILED = 101
}
