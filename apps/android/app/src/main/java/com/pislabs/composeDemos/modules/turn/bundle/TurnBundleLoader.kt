package com.pislabs.composeDemos.modules.turn.bundle

import android.annotation.SuppressLint
import android.app.Application
import com.pislabs.composeDemos.modules.turn.TurnLog
import com.pislabs.composeDemos.modules.turn.common.TurnConstants.RN_ASSET_NAME
import com.pislabs.composeDemos.modules.turn.react.TurnReactNativeHost
import com.pislabs.composeDemos.modules.fs.FsManager
import com.pislabs.composeDemos.modules.common.UrlUtil

/**
 * TurnScriptLoader
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/6 17:59
 */
class TurnBundleLoader (
    private val application: Application,
    private val reactHost: TurnReactNativeHost,
) {
    private val TAG = "TurnScriptLoader"

    /**
     * 加载脚本
     */
    fun loadBundle(
        loadUrl: String?,
        debugMode: Boolean = false,
        callback: (success: Boolean, message: String?) -> Unit
    ) {
        if (loadUrl.isNullOrEmpty()) {
            TurnLog.e("$TAG loadBundle called but filePath is empty")
            callback(false, null)
            return
        }

        if (!UrlUtil.isHttpUrl(loadUrl)) {
            if (loadUrl.startsWith(RN_ASSET_NAME)) {
                loadBundleFromAssets(loadUrl, callback)
            } else {
                loadBundleFromFile(loadUrl, callback)
            }

            loadBundleFromFile(loadUrl, callback)
            return
        }

        // 调试模式类型
        if (debugMode) {
            TurnLog.d("$TAG loadBundle called but is debug type")
            callback(true, null)
            return
        }

        // http链接类型
        TurnLog.d("$TAG loadBundle called but is http type")
        TurnBundleManager.getBundleFile(loadUrl) { filePath, error ->
            error?.let {
                TurnLog.e("$TAG loadScript called but getBundleDownloadFile failure")
                callback(false, it.msg ?: "DOWNLOAD FAILED")
            }

            filePath?.let {
                loadBundleFromFile(it, callback)
            }
        }

        if (loadUrl.startsWith(RN_ASSET_NAME)) {
            loadBundleFromAssets(loadUrl, callback)
        } else {
            loadBundleFromFile(loadUrl, callback)
        }

    }

    /**
     * 从文件中加载Bundle
     */
    @SuppressLint("VisibleForTests")
    private fun loadBundleFromFile(
        filePath: String?,
        callback: (success: Boolean, message: String?) -> Unit
    ) {
        if (filePath.isNullOrEmpty()) {
            TurnLog.e("$TAG loadBundleFromFile called but assetUrl is empty")
            callback(false, null)
            return
        }

        if (!FsManager.isExist(filePath)) {
            TurnLog.e("$TAG loadScriptFromFile called but filePath not exist")
            callback(false, null)
            return
        }

        TurnLog.d("$TAG loadScriptFromFile called filePath=$filePath")
        val reactContext = reactHost.reactInstanceManager.currentReactContext
        if (reactContext == null) {
            callback.invoke(false, null)
            return
        }
        try {
            reactContext.catalystInstance.loadScriptFromFile(filePath, filePath, false)
            reactHost.runAfterBundleLoaded {
                callback.invoke(true, null)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            callback.invoke(false, null)
        }

    }

    /**
     * 中资源中加载Bundle
     */
    @SuppressLint("VisibleForTests")
    private fun loadBundleFromAssets(
        assetUrl: String?,
        callback: (success: Boolean, message: String?) -> Unit
    ) {
        if (assetUrl.isNullOrEmpty()) {
            TurnLog.e("$TAG loadBundleFromAssets called but assetUrl is empty")
            callback(false, null)
            return
        }

        TurnLog.d("$TAG loadBundleFromAssets called assetUrl=$assetUrl")
        val reactContext = reactHost.reactInstanceManager.currentReactContext
        if (reactContext == null) {
            callback(false, null)
            return
        }

        try {
            reactContext.catalystInstance.loadScriptFromAssets(
                application.assets, getAssetUrl(assetUrl), false
            )
            reactHost.runAfterBundleLoaded {
                callback(true, null)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            callback(false, null)
        }
    }

    /**
     * 获取资源地址
     */
    private fun getAssetUrl(assetUrl: String): String {
        val prefix = RN_ASSET_NAME
        return if (!assetUrl.startsWith(prefix)) "${prefix}$assetUrl" else assetUrl
    }

    class Builder (
        private val application: Application,
        private val reactHost: TurnReactNativeHost,
    ) {
        fun build() : TurnBundleLoader {
            return TurnBundleLoader(application, reactHost)
        }
    }
}
