package com.pislabs.composeDemos.ui.react

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.react.ReactRootView
import com.pislabs.composeDemos.app.MainApplication
import com.pislabs.composeDemos.modules.turn.TurnView
import com.pislabs.composeDemos.modules.turn.common.LaunchOptions
import com.pislabs.composeDemos.ui.theme.ComposeDemosTheme

class TurnDemoActivity : ComponentActivity() {
//    lateinit var mTurnRootView: ReactRootView
    lateinit var mTurnRootView: TurnView


    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, TurnDemoActivity::class.java)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mTurnRootView = TurnView(this)
//        mTurnRootView = ReactRootView(this)

        setContent {
            ComposeDemosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        mTurnRootView,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }

//        val reactHost = (this.application as MainApplication).reactNativeHost
//
//        mTurnRootView.startReactApplication(
//            reactHost.reactInstanceManager, "HelloWorld", null)

        mTurnRootView.startApp(
            LaunchOptions(
                appKey = "",
                moduleName = "HelloWorld",
                loadUrl = "http://localhost:8081/index.bundle?platform=android&minify=false&app=android&dev=true&lazy=true&app=com.pislabs.composeDemos&modulesOnly=false&runModule=true",
                debugMode = true
            )
        )
    }
}

@Composable
fun Greeting(rnRootView: ReactRootView, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = {
            rnRootView
//            rnRootView.matchParentLayout()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeDemosTheme {
        Greeting(ReactRootView(LocalContext.current))
    }
}
