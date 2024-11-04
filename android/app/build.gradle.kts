plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    id("com.facebook.react")
}

android {
    namespace = "com.pislabs.composeDemos"
    compileSdk = BuildVal.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.pislabs.composeDemos"
        minSdk = BuildVal.MIN_SDK_VERSION
        targetSdk = BuildVal.TARGET_SDK_VERSION

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("Boolean", "IS_NEW_ARCHITECTURE_ENABLED", "${BuildVal.IS_NEW_ARCHITECTURE_ENABLED}")
        buildConfigField("Boolean", "IS_HERMES_ENABLED", "${BuildVal.IS_HERMES_ENABLED}")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.1"
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    // Set KSP sourceSet
    applicationVariants.all {
        val variantName = name
        sourceSets {
            getByName("main") {
                java.srcDir(File("build/generated/ksp/$variantName/kotlin"))
            }
        }
    }
}

react {
    // Needed to enable Autolinking - https://github.com/react-native-community/cli/blob/master/docs/autolinking.md
    autolinkLibrariesWithApp()
}

dependencies {
    // Note: we intentionally don't specify the version number here as RNGP will take care of it.
    // If you don't use the RNGP, you'll have to specify version manually.
    implementation(libs.react.android)

    if (BuildVal.IS_HERMES_ENABLED) {
        implementation(libs.hermes.android)
    } else {
        implementation("org.webkit:android-jsc:+")
    }

    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)
    implementation(platform(libs.koin.bom))

    implementation(libs.androidx.constraintlayout.compose)

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
//    implementation(libs.androidx.constraintlayout)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // 扩展图标
    implementation(libs.androidx.material.icons.extended)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}