package com.pislabs.composeDemos.ui.react

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.facebook.react.ReactFragment
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.pislabs.composeDemos.R

class ReactFrameActivity : AppCompatActivity(), DefaultHardwareBackBtnHandler {
    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, ReactFrameActivity::class.java)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_react_frame)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btn_loadRNPage).setOnClickListener {
            val reactNativeFragment = ReactFragment.Builder()
                .setComponentName("App")
                .setLaunchOptions(Bundle().apply { putString("message", "my value") })
                .build()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.react_native_fragment, reactNativeFragment)
                .commit()
        }
    }

    override fun invokeDefaultOnBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }
}