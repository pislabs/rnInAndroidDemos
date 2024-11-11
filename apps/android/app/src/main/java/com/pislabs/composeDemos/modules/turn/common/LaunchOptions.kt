package com.pislabs.composeDemos.modules.turn.common

import android.os.Bundle
import androidx.annotation.Keep

/**
 * LaunchOptions
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/6 15:03
 */
@Keep
data class LaunchOptions(
    var appKey: String = "",
    var moduleName: String = "",
    var version: String = "",
    var loadUrl: String = "",
    var downloadUrl: String = "",
    var closeNumber: String = "",
    var openOption: String = "",
    var md5: String = "",
    var debugMode: Boolean = false,

) {
    val bundle = Bundle()

    @JvmField
    var callbackMethods: Array<String>? = null

    override fun toString(): String {
        return "LaunchOptions {" +
                "appKey='" + appKey + '\'' +
                ", loadUrl='" + loadUrl + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", version='" + version + '\'' +
                ", closeNumber='" + closeNumber + '\'' +
                ", openOption=" + openOption +
                ", methods=" + callbackMethods?.contentToString() +
                ", bundle=" + bundle +
                '}'
    }

    companion object {
        fun parseUrl(url: String?): LaunchOptions? {
            if (url.isNullOrEmpty()) return null

            return LaunchOptions()
        }
    }
}
