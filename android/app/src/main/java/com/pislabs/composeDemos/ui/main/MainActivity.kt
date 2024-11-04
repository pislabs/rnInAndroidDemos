package com.pislabs.composeDemos.ui.main

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.pislabs.composeDemos.ui.theme.ComposeDemosTheme
import com.pislabs.composeDemos.ui.cmpts.Greeting

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            ComposeDemosTheme {
                Greeting(
                    name = "Android",
                    modifier = Modifier.fillMaxSize().safeDrawingPadding()
                )
            }
        }
    }
}
