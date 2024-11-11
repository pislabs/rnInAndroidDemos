package com.pislabs.composeDemos.modules.turn

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.WindowManager.LayoutParams
import androidx.annotation.MainThread
import com.facebook.react.ReactRootView
import com.facebook.react.bridge.UiThreadUtil
import com.pislabs.composeDemos.modules.turn.bundle.TurnBundleLoader
import com.pislabs.composeDemos.modules.turn.common.LaunchOptions
import com.pislabs.composeDemos.modules.turn.react.TurnReactNativeHost

/**
 * TurnView
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/5 16:56
 */
open class TurnView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ReactRootView(context, attrs, defStyle) {
    companion object {
        private const val TAG = "TurnReactView"
    }

    // 启动选项对象，包含了所有的信息。
    var launchOptions: LaunchOptions? = null
        private set

    var mReactNativeHost: TurnReactNativeHost? = null
        private set

    private val debugMode : Boolean
        get() = this.launchOptions?.debugMode ?: false

    // 设置完整布局
    fun matchParentLayout() : TurnView {
        val layoutParams = LayoutParams()

        layoutParams.height = LayoutParams.MATCH_PARENT
        layoutParams.width = LayoutParams.MATCH_PARENT

        this.layoutParams = layoutParams

        return this
    }

    @MainThread
    fun startApp(launchOptions: LaunchOptions) {
        this.launchOptions = launchOptions

        if (reactInstanceManager != null) {
            unmountReactApplication()
            mReactNativeHost?.clear()
            mReactNativeHost = null
        }

        runAfterReactHostReady { loadReactBundle() }
    }

    /**
     * 加载脚本
     */
    private fun loadReactBundle() {
        val host = getReactNativeHost()

        val loadUrl = launchOptions?.loadUrl.orEmpty()

//        if (debugMode) {
//            val uri = Uri.parse(loadUrl)
//            setDebugServerHost(uri.authority)
//        }

        if (host.isReady) {
            handleBundleLoadSuccess()
            return
        }

        ensureOnHostResume()

        TurnBundleLoader.Builder(TurnManager.application, host)
            .build()
            .loadBundle(loadUrl, debugMode) { success, _ ->
            UiThreadUtil.runOnUiThread {
                if (success) {
                    handleBundleLoadSuccess()
                } else {
                    handleBundleLoadFailure()
                }
            }
        }
    }

    private fun handleBundleLoadSuccess() {
        // 非预加载、缓存类型、调试模式等，缓存ReactHost
        if (!debugMode) {
            launchOptions?.let {
//                ZRN.reactHostManager?.cacheReactHost(
//                    it.appKey, it.downloadUrl, getZRNReactNativeHost()
//                )
            }
        }

        loadApp(launchOptions?.moduleName.orEmpty())
    }

    private fun handleBundleLoadFailure() {
    }

    @MainThread
    private fun loadApp(moduleName: String) {
        tryCatch(true) {
            ensureOnHostResume()
            startReactApplication(
                getReactNativeHost().reactInstanceManager, moduleName, launchOptions?.bundle
            )
//            onLoadAppListener?.onLoadComplete()
        }
    }

    /**
     * 启动调试Host
     */
    private fun setDebugServerHost(hostAndPort: String?) {
        TurnLog.d("$TAG setDebugServerHost called hostAndPort=$hostAndPort")
        ensureOnHostResume()
        tryCatch(true) {
            val devSettings =
                getReactNativeHost().reactInstanceManager.devSupportManager.devSettings

            if (devSettings != null) {
                devSettings.packagerConnectionSettings.debugServerHost = hostAndPort!!
            }
        }
    }

    /**
     * RN Host准备好了之后执行
     */
    private fun runAfterReactHostReady(runnable: Runnable) {
        if (debugMode) {
            runnable.run()
            return
        }

        getReactNativeHost().runAfterReady(runnable)
    }

    /**
     * 获取RN Host
     */
    private fun getReactNativeHost(): TurnReactNativeHost {
        if (mReactNativeHost == null) {
            mReactNativeHost = if (debugMode) {
                TurnManager.reactHostManager.getDevReactHost()
            } else {
                TurnManager.reactHostManager.getReactHost()
            }
        }

        return mReactNativeHost!!
    }

    private fun ensureOnHostResume() {
//        if (!mCurrentActivitySet && mCurrentActivity != null) {
//            TurnLog.d("$TAG ensureOnHostResume called activity=$mCurrentActivity")
//            tryCatch {
//                if (mCurrentActivity is DefaultHardwareBackBtnHandler) {
//                    mReactNativeHost?.reactInstanceManager?.onHostResume(
//                        mCurrentActivity, mCurrentActivity as? DefaultHardwareBackBtnHandler
//                    )
//                } else {
//                    mReactNativeHost?.reactInstanceManager?.onHostResume(mCurrentActivity)
//                }
//            }
//            mCurrentActivitySet = true
//        }
    }

    private fun tryCatch(handle: Boolean = false, block: () -> Unit) {
        try {
            block.invoke()
        } catch (e: Throwable) {
            e.printStackTrace()
            if (handle) {
                if (e is Exception) {
                    mReactNativeHost?.handleException(e)
                } else {
                    mReactNativeHost?.handleException(Exception(e.message))
                }
            }
        }
    }
}
