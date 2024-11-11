package com.pislabs.composeDemos.modules.turn

import android.app.Application
import com.facebook.react.ReactPackage
import com.facebook.soloader.SoLoader
import com.pislabs.composeDemos.modules.turn.bundle.TurnBundleManager
import com.pislabs.composeDemos.modules.turn.react.TurnReactHostManager

/**
 * TurnManager
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/5 14:43
 */
object TurnManager {
    private const val TAG = "TurnManager"

    private val reactPackages = mutableListOf<ReactPackage>()
    private val nativeModuleClass = mutableListOf<Class<*>>()
    private val viewManagerClass = mutableListOf<Class<*>>()

    var initialized = false
        private set

    lateinit var application: Application
        private set

    lateinit var reactHostManager: TurnReactHostManager
        private set

    // js主模块名字，默认"index"。
    var jsMainModuleName: String = "index"


    // asset中基础包的名字，默认"turn-common.jsbundle"。默认的基础包已内置到本SDK的aar包中。
    var commonBundleAssetName: String = "turn-common.jsbundle"

    /**
     * 初始化
     */
    fun init(context: Application) {
        if (initialized) return

        application = context

        initSoLoader()

        TurnBundleManager.init(context)

        reactHostManager = TurnReactHostManager(
            context, jsMainModuleName, commonBundleAssetName, reactPackages
        )

        initialized = true
    }

    /**
     * 初始化ReactNative框架的[SoLoader]，尽可能在[Application.onCreate]中执行。
     *
     * 非必须执行，但如果遇到了异常报错则必须执行该方法。
     *
     * @param application Application实例
     */
    private fun initSoLoader() {
        // 解决RN报异常: java.lang.RuntimeException: SoLoader.init() not yet called.
        try {
            SoLoader.init(application, false)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    // 添加自定义的ReactPackage，注意不要产生注册功能的重复。
    fun addReactPackage(list: List<ReactPackage>) {
        reactPackages.addAll(list)
    }

    // 向{@link TurnReactPackage}中添加NativeModule，若getName()同名则会覆盖已存在。
    fun addNativeModules(list: MutableList<Class<*>>) {
        nativeModuleClass.addAll(list)
    }

    fun getNativeModules(): List<Class<*>> = nativeModuleClass

    // 向{@link TurnReactPackage}中添加ViewManager，若getName()同名则会覆盖已存在。
    fun addViewManagers(list: MutableList<Class<*>>) {
        viewManagerClass.addAll(list)
    }

    fun getViewManagers(): List<Class<*>> = viewManagerClass
}
