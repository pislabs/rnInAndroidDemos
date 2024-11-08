package com.pislabs.composeDemos.ui.react

import android.content.Context
import android.content.Intent
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate

/**
 * MainReactActivity
 * @author rayl
 * @e-mail liuyi86@zto.com
 * @date 2024/11/4 09:56
 */
class ReactMainActivity : ReactActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, ReactMainActivity::class.java)
            )
        }
    }

    override fun getMainComponentName(): String {
        return "HelloWorld"
    }

    override fun createReactActivityDelegate(): ReactActivityDelegate {
        return DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)
    }
}