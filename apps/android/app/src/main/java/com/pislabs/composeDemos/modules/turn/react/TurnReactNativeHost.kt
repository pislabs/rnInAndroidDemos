package com.pislabs.composeDemos.modules.turn.react

import android.app.Application
import com.facebook.react.PackageList
import com.facebook.react.ReactInstanceEventListener
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.JavaScriptExecutorFactory
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.UiThreadUtil
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.react.runtime.JSCInstance
import com.facebook.react.runtime.hermes.HermesInstance
import com.pislabs.composeDemos.BuildConfig
import com.pislabs.composeDemos.modules.turn.TurnLog

/**
 * TreacHost
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/5 14:52
 */
class TurnReactNativeHost : DefaultReactNativeHost {
    companion object {
        const val TAG = "TurnReactHost"
    }

    private val jsMainModuleName: String?
    private val bundleAssetName: String?
    private val reactPackages: List<ReactPackage>?
    private val useDeveloperSupport: Boolean

//    private val mExceptionHandlers = CopyOnWriteArrayList<NativeModuleCallExceptionHandler>()

    @Volatile
    var isReady = false // 基础包加载完成
        private set

    constructor(
        application: Application,
        jsMainModuleName: String?,
        bundleAssetName: String?,
        reactPackages: List<ReactPackage>?,
        useDeveloperSupport: Boolean
    ) : super(application) {
        this.jsMainModuleName = jsMainModuleName
        this.bundleAssetName = bundleAssetName
        this.reactPackages = reactPackages
        this.useDeveloperSupport = useDeveloperSupport

        // 开发模式 直接 ready 无需准备
        if (useDeveloperSupport) {
            isReady = true
        }
    }

    override fun getJSMainModuleName(): String {
        return jsMainModuleName ?: super.getJSMainModuleName()
    }

    override fun getBundleAssetName(): String? {
        return if (useDeveloperSupport) super.getBundleAssetName() else bundleAssetName ?: super.getBundleAssetName()
    }

    override fun getUseDeveloperSupport(): Boolean {
        return useDeveloperSupport
    }

    override fun getPackages(): MutableList<ReactPackage> {
//        val subList: MutableList<ReactPackage> = ArrayList()
//        if (!reactPackages.isNullOrEmpty()) {
//            subList.addAll(reactPackages)
//        }
//        return subList
        return PackageList(this).packages
    }

    override val isNewArchEnabled: Boolean
        get() = BuildConfig.IS_RN_NEW_ARCHITECTURE_ENABLED

    override val isHermesEnabled: Boolean
        get() = BuildConfig.IS_RN_HERMES_ENABLED

//    override fun getJavaScriptExecutorFactory(): JavaScriptExecutorFactory? {
////        return JSCInstance()
////        return  if (isHermesEnabled) (HermesInstance()) else (JSCInstance());
//    }

    fun prepare(): TurnReactNativeHost {
        if (isReady) return this

        // 触发在后台异步任务中异步响应上下文初始化。
        // 这使应用程序可以预加载应用程序JS，并在ReactRootView可用和measured已测量之前执行全局代码。
        reactInstanceManager.createReactContextInBackground()

        runAfterBundleLoaded{
            TurnLog.d("$TAG prepare success")
        }

        return this
    }

    /**
     * 运行在基础包加载完成之后
     */
    fun runAfterReady(runnable: Runnable) {
        if (isReady) {
            runnable.run()
        } else {
            runAfterBundleLoaded(runnable)
        }
    }

    /**
     * 运行在包加载完成之后
     */
    fun runAfterBundleLoaded(runnable: Runnable) {
        val reactContext = reactInstanceManager.currentReactContext

        if (reactContext != null) {
            reactContext.runOnJSQueueThread {
                isReady = true
                UiThreadUtil.runOnUiThread(runnable)
            }
        } else {
            reactInstanceManager.addReactInstanceEventListener(object :
                ReactInstanceEventListener {
                override fun onReactContextInitialized(context: ReactContext) {
                    isReady = true
                    reactInstanceManager.removeReactInstanceEventListener(this)
                    context.runOnJSQueueThread{ UiThreadUtil.runOnUiThread(runnable) }
                }
            })
        }
    }

    fun handleException(e: Exception) {
        TurnLog.e("$TAG onHandleException called message=${e.message}")
//        val it: Iterator<NativeModuleCallExceptionHandler> = mExceptionHandlers.iterator()
//        while (it.hasNext()) {
//            try {
//                it.next().handleException(e)
//            } catch (exception: Exception) {
//                exception.printStackTrace()
//            }
//        }
    }
}
