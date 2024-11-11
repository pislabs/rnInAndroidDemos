package com.pislabs.composeDemos.modules.turn.react

import android.app.Application
import com.facebook.react.ReactPackage
import com.pislabs.composeDemos.modules.turn.TurnLog

/**
 * TurnReactHostManager
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/6 14:23
 */
class TurnReactHostManager(
    private val application: Application,
    private val jsMainModuleName: String?,
    private val bundleAssetName: String?,
    private val reactPackages: List<ReactPackage>?
) {
    companion object {
        const val TAG = "TurnReactHostManager"
    }

    private var mHost: TurnReactNativeHost? = null

    // 获取调试Host
    fun getDevReactHost(): TurnReactNativeHost {
        val host = createReactHost(true)
        return host
    }

    // 获取Host
    fun getReactHost(): TurnReactNativeHost {
        if (mHost == null) {
            TurnLog.d("$TAG getReactHost called get from prepare")
            prepareReactHost()
        } else {
            TurnLog.d("$TAG getReactHost called get from cache")
        }

        return mHost!!
    }

    /**
     * 准备ReactHost
     * 生产上会有多线程的场景，为了保证可靠性，需要更可靠的缓存
     */
    private fun prepareReactHost() {
        TurnLog.d("$TAG prepareReactHost called")

        mHost = createReactHost().prepare()
    }

    /**
     * 创建ReactHost
     */
    private fun createReactHost(useDeveloperSupport: Boolean = false): TurnReactNativeHost {
        return TurnReactNativeHost(
            application,
            jsMainModuleName,
            bundleAssetName,
            reactPackages,
            useDeveloperSupport
        )
    }
}
